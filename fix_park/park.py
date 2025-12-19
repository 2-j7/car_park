from flask import Flask, request, jsonify
from flask_cors import CORS
from paddleocr import PaddleOCR

import re
import cv2


app = Flask(__name__)
CORS(app)

#print("正在加载 AI 模型...")
# 添加 enable_mkldnn=True 开启 CPU 加速
ocr = PaddleOCR(use_textline_orientation=True, lang="ch", enable_mkldnn=True)
print("模型加载完成")


# --- 核心：车牌智能处理逻辑 ---
def intelligent_plate_extraction(ocr_result):
    """
    兼容新旧版本 PaddleOCR 的数据清洗与车牌提取
    """
    if not ocr_result:
        return "未识别"

    # 正则规则：[省份][字母][5-6位字母或数字]
    plate_pattern = re.compile(
        r'^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z][A-Z0-9]{5,6}$')

    candidates = []

    # === 分支 A: 处理新版 PaddleOCR (字典格式) ===
    # 结构: [{'rec_texts': ['苏A12345', ...], 'rec_scores': [0.99, ...], ...}]
    if isinstance(ocr_result, list) and len(ocr_result) > 0 and isinstance(ocr_result[0], dict):
        data = ocr_result[0]
        if 'rec_texts' in data and 'rec_scores' in data:
            texts = data['rec_texts']
            scores = data['rec_scores']
            # 将文本和分数配对处理
            for raw_text, score in zip(texts, scores):
                process_candidate(raw_text, score, candidates, plate_pattern)

    # === 分支 B: 处理旧版 PaddleOCR (列表格式) ===
    # 结构: [ [ [[box], [text, score]], ... ] ]
    elif isinstance(ocr_result, list) and len(ocr_result) > 0 and isinstance(ocr_result[0], list):
        for line in ocr_result[0]:
            if line and len(line) >= 2:
                raw_text = line[1][0]
                score = line[1][1]
                process_candidate(raw_text, score, candidates, plate_pattern)

    if not candidates:
        return "未识别"

    # 择优录取：返回置信度最高的那一个
    best_candidate = max(candidates, key=lambda x: x['score'])
    return best_candidate['text']


def process_candidate(raw_text, score, candidates, pattern):
    """辅助函数：清洗文本并匹配正则"""
    # 清洗：去掉点、空格、横杠
    clean_text = raw_text.replace('·', '').replace('.', '').replace(' ', '').replace('-', '').upper()

    # 简单纠错：车牌第2位一定是字母 (0->D, 8->B)
    if len(clean_text) >= 7:
        text_list = list(clean_text)
        if text_list[1] == '0': text_list[1] = 'D'
        if text_list[1] == '8': text_list[1] = 'B'
        clean_text = "".join(text_list)

    # 正则匹配
    if pattern.match(clean_text):
        candidates.append({'text': clean_text, 'score': score})
        print(f"发现疑似车牌: {clean_text} (置信度: {score})")


# --- 图像预处理 ---
# --- 图像预处理 (优化版：限制最大尺寸，提升速度) ---
def preprocess_image(save_path):
    try:
        img = cv2.imread(save_path)
        if img is None: return

        h, w = img.shape[:2]

        # 1. 如果图片太大(宽或高超过1280)，进行缩小，显著提升速度
        if w > 1280 or h > 1280:
            scale = 1280 / max(w, h)
            img = cv2.resize(img, None, fx=scale, fy=scale, interpolation=cv2.INTER_AREA)
            print(f"图片过大，已压缩至 {img.shape[1]}x{img.shape[0]}")

        # 2. 如果图片太小(高度小于50)，进行放大，提升识别率
        elif h < 50:
            img = cv2.resize(img, None, fx=2.0, fy=2.0, interpolation=cv2.INTER_CUBIC)

        # 3. 转灰度 (去除颜色干扰)
        gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

        cv2.imwrite(save_path, gray)
        print("图像预处理完成")
    except Exception as e:
        print(f"跳过预处理: {e}")

@app.route('/ocr', methods=['POST'])
def ocr_service():
    if 'file' not in request.files:
        return jsonify({"error": "No file"}), 400

    file = request.files['file']
    save_path = "temp.jpg"
    file.save(save_path)

    print(f"接收图片: {file.filename}")

    # 1. 图像增强
    preprocess_image(save_path)

    try:
        # 2. AI 识别 (修复点：去掉了 det=True, rec=True)
        # 仅传入图片路径，新版模型会自动处理检测和识别
        result = ocr.ocr(save_path)

        # 3. 智能提取
        plate = intelligent_plate_extraction(result)

        print(f"最终判定结果: {plate}")
        return jsonify({"plate": plate})

    except Exception as e:
        print(f"识别过程报错: {e}")
        import traceback
        traceback.print_exc()
        return jsonify({"plate": "识别出错"}), 500


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True, use_reloader=False)
