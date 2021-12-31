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


def query_proposal():
    url: str = "http://localhost:8080/api/orchestrator/queryMessage"
    get(url, stream=True)


def send_intent(user_id: str, proposed_user_id: str):
    url: str = "http://localhost:8080/api/orchestrator/intentMessage?"
    url += f"userID={user_id}&proposed_userID={proposed_user_id}"
    get(url, stream=True)


def check_intent(user_id: str):
    url: str = f"http://localhost:8080/api/orchestrator/checkIntent?userID={user_id}"
    response = get(url, stream=True)
    print("Client recieved", response.json())
