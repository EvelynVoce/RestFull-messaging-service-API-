from requests import get
from geopy.geocoders import Nominatim


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


def query_proposal():
    url: str = "http://localhost:8080/api/orchestrator/queryMessage"
    response = get(url, stream=True)
    print("Client received", response.json())


def send_intent(user_id: str, proposed_user_id: str):
    url: str = "http://localhost:8080/api/orchestrator/intentMessage?"
    url += f"userID={user_id}&proposed_userID={proposed_user_id}"
    get(url, stream=True)


def check_intent(user_id: str):
    url: str = f"http://localhost:8080/api/orchestrator/checkIntent?userID={user_id}"
    response = get(url, stream=True)
    print("Client received", response.json())
