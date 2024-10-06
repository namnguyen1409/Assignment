import requests
from PIL import Image

# test upload file

url = 'http://localhost:8080/assignment/api/upload/image/avatar'

user_agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.5112.102 Safari/537.36 Edg/104.0.1293.63"

headers = {'User-Agent': user_agent}


# create random image file

img = Image.new('RGB', (60, 30), color = 'red')

img.save('test.jpg')

files = {'file': open('test.jpg', 'rb')}

r = requests.post(url, files=files, headers=headers)

print(r.text)