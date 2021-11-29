from django.shortcuts import render

# Create your views here.

from django.http import HttpResponse
import requests
from bs4 import BeautifulSoup as Soup


def generate_id(request) -> HttpResponse:
    link: str = "https://www.random.org/strings/?"
    params: dict = dict(
        num='1', len='16',
        digits='on', upperalpha='on',
        loweralpha="on", unique="on",
        format="html", rnd="new"
    )
    resp = requests.get(url=link, params=params)
    page_soup = Soup(resp.text, "lxml")
    generated_id: str = page_soup.find("pre", {"class": "data"}).text.strip()
    return HttpResponse(generated_id)
