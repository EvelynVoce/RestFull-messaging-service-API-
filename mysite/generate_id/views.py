from django.shortcuts import render

# Create your views here.

from django.http import HttpResponse
import requests
from json import dumps


def generate_id(request) -> HttpResponse:
    link: str = "https://www.random.org/strings/?"
    params: dict = dict(
        num='1', len='16',
        digits='on', upperalpha='on',
        loweralpha="on", unique="on",
        format="plain", rnd="new"
    )
    resp = requests.get(url=link, params=params)
    temp = {'id': resp.text}
    id_json = dumps(temp)
    return HttpResponse(id_json)
