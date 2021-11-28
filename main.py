import tkinter as tk
from tkinter import font
import external_services

bg_col: str = "grey"
fg_col: str = "white"
button_col: str = "dark grey"

root = tk.Tk()
root.geometry("{0}x{1}+0+0".format(root.winfo_screenwidth(), root.winfo_screenheight()))
root.title("SCC")
root.config(bg=bg_col)


def underline(label):
    f = font.Font(label, label.cget("font"))
    f.configure(underline=True)
    label.configure(font=f)


def update_id_box(id_box):
    id_box.config(state=tk.NORMAL)
    id_box.delete('1.0', tk.END)
    id_box.insert(tk.INSERT, external_services.generate_id())
    id_box.config(state=tk.DISABLED)


def main():
    page_title = tk.Label(root, text="SCC: Trips", font=("arial", 28, "bold"), fg=fg_col, bg=bg_col)
    page_title.place(relx=0.5, rely=0.05, anchor=tk.CENTER)
    underline(page_title)

    id_box = tk.Text(root, wrap=tk.WORD, cursor="arrow", bd=8, relief=tk.GROOVE, font=("arial", 15))
    id_box.place(relx=0.78, rely=0.025, relwidth=0.19, relheight=0.05)
    id_box.config(state="disabled")
    gen_id_button = tk.Button(root, text="Generate ID", font=("arial", 10, "bold"),
                              bg=button_col, command=lambda: update_id_box(id_box))
    gen_id_button.place(relx=0.70, rely=0.05, relwidth=0.15, relheight=0.05, anchor=tk.CENTER)


if __name__ == "__main__":
    main()
    tk.mainloop()
