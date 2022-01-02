from requests import get
from geopy.geocoders import Nominatim
import tkinter as tk
import json


def get_id() -> str:
    url: str = "http://localhost:8080/api/orchestrator?service=ID"
    response = get(url, stream=True)
    return response.json()["id"]


def submit_proposal(user_id, location, date) -> None:
    url: str = "http://localhost:8080/api/orchestrator/submitTrip?"
    geolocator = Nominatim(user_agent="Evelyn Voce")
    place = geolocator.geocode(location)
    url += f"userID={user_id}&location={location}&date={date}&lat={place.latitude}&lon={place.longitude}"
    get(url, stream=True)


def query_proposal_func(text_box):
    url: str = "http://localhost:8080/api/orchestrator/queryMessage"
    response = get(url, stream=True).json()
    json_dict: dict = json.loads(response["message"])
    print("Client received proposal", json_dict)

    json_weather: dict = json_dict['weather']
    json_temps: dict = json_weather['temp2m']
    message: str = f"Proposal: {json_dict['userID']}. Wants to go to {json_dict['location']} on {json_dict['date']}" \
                   f". The weather will be {json_weather['weather']} with lowest temperatures of" \
                   f" {json_temps['min']} and highs of {json_temps['max']} degrees celsius"
    text_box.config(state=tk.NORMAL)
    text_box.insert(tk.INSERT, message)
    text_box.config(state=tk.DISABLED)


def send_intent(user_id: str, proposed_user_id: str):
    url: str = "http://localhost:8080/api/orchestrator/intentMessage?"
    url += f"userID={user_id}&proposed_userID={proposed_user_id}"
    get(url, stream=True)


def check_intent(user_id: str, intent_box):
    url: str = f"http://localhost:8080/api/orchestrator/checkIntent?userID={user_id}"
    response = get(url, stream=True).json()
    print("Client received intent", response)

    json_dict: dict = json.loads(response["message"])
    print("Client received proposal", json_dict)

    message: str = f"Intent: {json_dict['userID']}, would like to join you on your trip"
    intent_box.config(state=tk.NORMAL)
    intent_box.insert(tk.INSERT, message)
    intent_box.config(state=tk.DISABLED)
