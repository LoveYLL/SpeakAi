# from fastapi import FastAPI, Request, Response, Form
# from pydantic import BaseModel
# from gtts import gTTS
# import io
#
# TTS = FastAPI()
#
#
#
# @TTS.post("/tts")
# async def tts_api(req: str = Form(...)):
#     tts = gTTS(text=req, lang='en')
#     mp3_fp = io.BytesIO()
#     tts.write_to_fp(mp3_fp)
#     mp3_fp.seek(0)
#     return Response(content=mp3_fp.read(), media_type="audio/mpeg")