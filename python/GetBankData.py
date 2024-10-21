import requests
import concurrent.futures
import os

imgPath = r"C:\Users\namnguyen\OneDrive\Documents\NetBeansProjects\Assignment\src\main\webapp\resources\images\ewallet\bank"

def writeSql(bank_name, bank_code, bank_short_name, bank_logo):
    sql = f"INSERT INTO bank (name, code, short_name, logo) VALUES (N'{bank_name}', N'{bank_code}', N'{bank_short_name}', N'{bank_logo}');"
    with open("bank.sql", "a", encoding="utf-8") as f:
        f.write(sql + "\n")

def download_image(img_url, img_path):
    img = requests.get(img_url)
    with open(img_path, "wb") as f:
        f.write(img.content)

def process_bank(bank):
    bank_name = bank['name']
    bank_code = bank['code']
    bank_short_name = bank['short_name']
    bank_logo = bank['logo'].split("/")[-1]
    writeSql(bank_name, bank_code, bank_short_name, bank_logo)

    # download image
    img_url = bank['logo']
    img_path = os.path.join(imgPath, bank_logo)
    download_image(img_url, img_path)

api_url = "https://api.vietqr.io/v2/banks"

headers = {
    'user-agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3',
}

response = requests.get(api_url, headers=headers, verify=False, stream=True)
data = response.json()

# Use ThreadPoolExecutor to download images concurrently
with concurrent.futures.ThreadPoolExecutor() as executor:
    futures = [executor.submit(process_bank, bank) for bank in data['data']]
    concurrent.futures.wait(futures)

print("Finished")
