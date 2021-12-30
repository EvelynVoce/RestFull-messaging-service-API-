from requests import get


def get_id() -> str:
    url: str = "http://localhost:8080/api/orchestrator?service=ID"
    response = get(url, stream=True)
    print(json_id := response.json()["id"])
    return json_id


def submit_proposal(user_id, location, date) -> None:
    url: str = "http://localhost:8080/api/orchestrator/submitTrip?"
    url += f"userID={user_id}&location={location}&date={date}"
    get(url, stream=True)
