import OSS from "ali-oss";

const client = new OSS({
    region: "oss-cn-hangzhou",
    accessKeyId: "LTAI5tBUR4DtAn3KgjMcgxWf",
    accessKeySecret: "W8rfl3Pti8c46qDLO0aBEDU3UXvyVC",
    bucket: "speakai-voice"
})

export default client

