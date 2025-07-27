from fastapi import FastAPI, UploadFile, File
import whisper
import tempfile
import shutil

app = FastAPI()
model = whisper.load_model("small")  # 可选：tiny / base / medium / large

@app.post("/whisper")
async def transcribe_audio(file: UploadFile = File(...)):
    with tempfile.NamedTemporaryFile(delete=False, suffix=".wav") as tmp:
        shutil.copyfileobj(file.file, tmp)
        tmp_path = tmp.name

    result = model.transcribe(tmp_path)
    return {"text": result["text"]}
