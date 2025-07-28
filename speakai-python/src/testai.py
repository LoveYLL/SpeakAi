# # -*- coding: utf-8 -*-
# import whisper
# # 模型加载（自动选GPU或CPU）
# model = whisper.load_model("small")
#
# # 音频文件路径，注意用r''避免转义问题
# audio_path = r"D:\mydownload\voice\新录音 11.mp3"
#
# # 转录音频
# result = model.transcribe(audio_path)
#
# # 打印识别文本
# print(result["text"])

import sys
import whisper

if len(sys.argv) < 2:
    print("请提供音频路径")
    sys.exit(1)

audio_path = sys.argv[1]
model = whisper.load_model("small")
result = model.transcribe(audio_path)
print(result["text"])
