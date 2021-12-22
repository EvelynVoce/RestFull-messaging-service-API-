from django.shortcuts import render

# Create your views here.

from django.http import HttpResponse
import requests
import xml.etree.ElementTree as Et
from json import dumps


def get_temp(request):
    base: str = "http://api.worldweatheronline.com/premium/v1/weather.ashx?key=750d0b6ed1b944699a0211535212711"
    resp = requests.get(url=base + "&q=London")
    xml_root = Et.fromstring(resp.text)
    current_condition = xml_root.find('current_condition')

    temp = {'temp_celsius': int(current_condition.find('temp_C').text)}
    temp_json = dumps(temp)
    return HttpResponse(temp_json)