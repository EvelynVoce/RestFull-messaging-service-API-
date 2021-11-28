import requests
from bs4 import BeautifulSoup as Soup
import xml.etree.ElementTree as Et


def generate_id():
    link: str = "https://www.random.org/strings/?"
    params: dict = dict(
        num='1', len='16',
        digits='on', upperalpha='on',
        loweralpha="on", unique="on",
        format="html", rnd="new"
    )
    resp = requests.get(url=link, params=params)
    page_soup = Soup(resp.text, "lxml")
    return page_soup.find("pre", {"class": "data"}).text.strip()


def get_temp():
    base: str = "http://api.worldweatheronline.com/premium/v1/weather.ashx?key=750d0b6ed1b944699a0211535212711"
    resp = requests.get(url=base + "&q=London")
    xml_root = Et.fromstring(resp.text)
    current_condition = xml_root.find('current_condition')
    return current_condition.find('temp_C').text

