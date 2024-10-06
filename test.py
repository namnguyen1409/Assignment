import requests
from PIL import Image

# test upload file

url = 'http://localhost:8080/assignment/api/upload/image/avatar'

user_agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.5112.102 Safari/537.36 Edg/104.0.1293.63"

headers = {'User-Agent': user_agent}

files = {'file': open(r'C:\Users\namnguyen\Downloads\z5783632876415_e287a5cab2adabcfc61d28c74c33a8de.jpg', 'rb')}

r = requests.post(url, files=files, headers=headers)

print(r.text)