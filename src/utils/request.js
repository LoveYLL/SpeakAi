import axios from "axios";
import qs from "qs";
const Req = axios.create({
  baseURL: import.meta.env.APP_BASE_URL,  
  //baseURL: "https://localhost:8080/api/",
});

Req.interceptors.request.use(function (config) {
    return config; 
});

Req.interceptors.response.use(function (response) {
    return response.data; 
});

function Get(url){
    return Req({
      url: url, 
      method: "get",
      params: {}
    });
}

function Post(url, data, isJson){
  let da = data    
  if(isJson){
    da = qs.stringify(data)
  }
  return Req({
    url: url, 
    method: "post",
    data: da
  });
}
function Postfile(url, data, refText){
    let formData = new FormData();
    if(refText){
      formData.append("refText", refText);
    }
    formData.append("file", data); 
    return Req({
      url: url, 
      method: "post",
      data: formData,
      headers: { 
        "Content-Type": "multipart/form-data",
      }
    })
}

function Postchat(url, request,data){
  let formData = new FormData();
  if(data){
    formData.append("file", data);
  }
  if(request.roleCode){
    formData.append("roleCode", request.roleCode);
  }
  if(request.text){
    formData.append("text", request.text);
  }
  if(request.workspace){
    formData.append("workspace", request.workspace);
  }
  return Req({
    url: url, 
    method: "post",
    data: formData,
    headers: { 
      "Content-Type": "multipart/form-data",
    }
  })
}




export {Get, Post, Postfile, Postchat};
