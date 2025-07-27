
function base64toBlob(base64, contentType) {
  var byteCharacters = atob(base64);
  var byteArrays = [];
  for (var i = 0; i < byteCharacters.length; i++) {
    byteArrays.push(byteCharacters.charCodeAt(i));
  }
  var byteArray = new Uint8Array(byteArrays);
  return new Blob([byteArray], {type: contentType});
}



export {  base64toBlob }
