import tempfile
import edge_tts
from fastapi import FastAPI, Form, Response
TTS = FastAPI()
# 全局变量保存上一次的 voice 设置
last_voice = "en-US-JennyNeural"
@TTS.post("/tts")
async def tts_api(
    req: str = Form("Hello, how are you?"),
    voice: str = Form(None)
):
    global last_voice
    # 如果传入了 voice 参数，就更新
    if voice:
        last_voice = voice
    communicate = edge_tts.Communicate(text=req, voice=last_voice)
    with tempfile.NamedTemporaryFile(delete=False, suffix=".mp3") as tmp:
        tmp_path = tmp.name
    await communicate.save(tmp_path)
    with open(tmp_path, "rb") as f:
        mp3_data = f.read()
    return Response(content=mp3_data, media_type="audio/mpeg")