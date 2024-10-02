import requests

r = requests.get("http://localhost:8080/assignment/register")
print(r.text)

print(r.status_code)