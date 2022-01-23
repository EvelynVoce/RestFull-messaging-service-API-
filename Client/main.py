import tkinter as tk
from tkinter import font
import run_orchestrator as run_orc
from threading import Thread
from datetime import datetime, timedelta
from tkinter import messagebox

bg_col: str = "grey"
fg_col: str = "white"
button_col: str = "dark grey"

root = tk.Tk()
root.geometry("{0}x{1}+0+0".format(root.winfo_screenwidth(), root.winfo_screenheight()))
root.title("SCC")
root.config(bg=bg_col)

ID: str = ""
text_box = tk.Text(root, wrap=tk.WORD, cursor="arrow", bd=8, relief=tk.GROOVE, font=("arial", 20), state=tk.DISABLED)
intent_box = tk.Text(root, wrap=tk.WORD, cursor="arrow", bd=8, relief=tk.GROOVE, font=("arial", 20), state=tk.DISABLED)


def underline(label):
    f = font.Font(label, label.cget("font"))
    f.configure(underline=True)
    label.configure(font=f)


def update_id_box(id_box):
    id_box.config(state=tk.NORMAL)
    id_box.delete('1.0', tk.END)
    global ID
    id_box.insert(tk.INSERT, ID := run_orc.get_id())
    id_box.config(state=tk.DISABLED)


def clear_root():
    for ele in root.winfo_children():
        ele.destroy()


def create_message_box():
    txt_box = tk.Text(root, wrap=tk.WORD, cursor="arrow", bd=8, relief=tk.GROOVE, font=("arial", 20), state=tk.DISABLED)
    txt_box.place(relx=0.025, rely=0.2, relwidth=0.93, relheight=0.7)
    scrollbar = tk.Scrollbar(root, command=txt_box.yview)
    scrollbar.place(relx=0.96, rely=0.2, relheight=0.7)
    return txt_box


def check_date(location: str, given_date: str):
    given_date_obj = datetime.strptime(given_date, "%Y%m%d").date()
    current_date = datetime.date(datetime.now())
    max_date = current_date + timedelta(days=7)
    if (given_date_obj > max_date) or (given_date_obj < current_date):
        messagebox.showinfo(message="Invalid date: Please pick a date no more than 7 days in the future")
    else:
        run_orc.submit_proposal(ID, location, given_date)
        clear_root()
        main()


def submit_proposal_ui():
    location_label = tk.Label(root, text="Location:", font=("arial", 15, "bold"), fg=fg_col, bg=bg_col)
    location_label.place(relx=0.20, rely=0.35)
    date_label = tk.Label(root, text="Date:", font=("arial", 15, "bold"), fg=fg_col, bg=bg_col)
    date_label.place(relx=0.20, rely=0.45)

    location = tk.Entry(root, relief=tk.GROOVE, bd=2, font=("arial", 13))
    location.place(relx=0.30, rely=0.35, relwidth=0.5, relheight=0.05)
    date = tk.Entry(root, relief=tk.GROOVE, bd=2, font=("arial", 13))
    date.place(relx=0.30, rely=0.45, relwidth=0.5, relheight=0.05)

    submit_proposal_button = tk.Button(root, text="Submit Proposal", font=("arial", 10, "bold"), bg=button_col,
                                       command=lambda: check_date(location.get(), date.get()))
    submit_proposal_button.place(relx=0.30, rely=0.2, relwidth=0.15, relheight=0.05, anchor=tk.CENTER)


def send_intent_ui():
    proposed_user_label = tk.Label(root, text="Proposed User ID:", font=("arial", 15, "bold"), fg=fg_col, bg=bg_col)
    proposed_user_label.place(relx=0.20, rely=0.35)

    proposed_user_entry = tk.Entry(root, relief=tk.GROOVE, bd=2, font=("arial", 13))
    proposed_user_entry.place(relx=0.30, rely=0.35, relwidth=0.5, relheight=0.05)

    submit_intent_button = tk.Button(root, text="Submit Intent", font=("arial", 10, "bold"), bg=button_col,
                                     command=lambda: run_orc.send_intent(ID, proposed_user_entry.get()) or
                                     clear_root() or main())
    submit_intent_button.place(relx=0.30, rely=0.2, relwidth=0.15, relheight=0.05, anchor=tk.CENTER)


def create_id_box():
    id_box = tk.Text(root, wrap=tk.WORD, cursor="arrow", bd=8, relief=tk.GROOVE, font=("arial", 15))
    id_box.insert(tk.END, ID)
    id_box.place(relx=0.78, rely=0.025, relwidth=0.19, relheight=0.05)
    id_box.config(state="disabled")
    return id_box


def show_intent():
    clear_root()
    global intent_box
    intent_box = create_message_box()
    Thread(target=run_orc.check_intent, args=(ID, intent_box), daemon=True).start()
    home_button = tk.Button(root, text="Home", font=("arial", 10, "bold"), bg=button_col,
                            command=lambda: clear_root() or main())
    home_button.place(relx=0.20, rely=0.15, relwidth=0.15, relheight=0.05, anchor=tk.CENTER)
    create_id_box()


def main():
    page_title = tk.Label(root, text="SCC: Trips", font=("arial", 28, "bold"), fg=fg_col, bg=bg_col)
    page_title.place(relx=0.5, rely=0.05, anchor=tk.CENTER)
    underline(page_title)

    global text_box
    text_box = create_message_box()
    run_orc.inserts(text_box, "", False)
    id_box = create_id_box()

    gen_id_button = tk.Button(root, text="Generate ID", font=("arial", 10, "bold"),
                              bg=button_col, command=lambda: Thread(target=update_id_box(id_box), daemon=True).start())
    gen_id_button.place(relx=0.70, rely=0.05, relwidth=0.15, relheight=0.05, anchor=tk.CENTER)

    submit_trip_proposal = tk.Button(root, text="Submit A Proposal", font=("arial", 10, "bold"),
                                     bg=button_col, command=lambda: clear_root() or submit_proposal_ui())
    submit_trip_proposal.place(relx=0.10, rely=0.05, relwidth=0.15, relheight=0.05, anchor=tk.CENTER)

    query_proposal = tk.Button(root, text="Query Proposal", font=("arial", 10, "bold"),
                               bg=button_col, command=lambda: Thread(target=run_orc.query_proposal_func,
                                                                     args=(text_box,), daemon=True).start())
    query_proposal.place(relx=0.30, rely=0.05, relwidth=0.15, relheight=0.05, anchor=tk.CENTER)

    send_intent_button = tk.Button(root, text="Send Intent", font=("arial", 10, "bold"),
                                   bg=button_col, command=lambda: clear_root() or Thread(target=send_intent_ui,
                                                                                         daemon=True).start())
    send_intent_button.place(relx=0.10, rely=0.15, relwidth=0.15, relheight=0.05, anchor=tk.CENTER)

    check_intent_button = tk.Button(root, text="Check Intent", font=("arial", 10, "bold"),
                                    bg=button_col, command=show_intent)
    check_intent_button.place(relx=0.30, rely=0.15, relwidth=0.15, relheight=0.05, anchor=tk.CENTER)

    search = tk.Button(root, text="Search", font=("arial", 10, "bold"), bg=button_col,
                       command=lambda: run_orc.inserts(text_box, search_box.get(), True))
    search.place(relx=0.70, rely=0.15, relwidth=0.15, relheight=0.05, anchor=tk.CENTER)

    search_box = tk.Entry(root, relief=tk.GROOVE, bd=8, font=("arial", 15))
    search_box.place(relx=0.78, rely=0.125, relwidth=0.19, relheight=0.05)

    reset_button = tk.Button(root, text="Show All", font=("arial", 10, "bold"), bg=button_col,
                             command=lambda: run_orc.inserts(text_box, search_box.get(), False))
    reset_button.place(relx=0.943, rely=0.15, relwidth=0.05, relheight=0.045, anchor=tk.CENTER)


if __name__ == "__main__":
    main()
    tk.mainloop()
