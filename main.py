import tkinter as tk

bg_col: str = "grey"
fg_col: str = "white"
button_col: str = "dark grey"

root = tk.Tk()
root.geometry("{0}x{1}+0+0".format(root.winfo_screenwidth(), root.winfo_screenheight()))
root.title("SCC")
root.config(bg=bg_col)

if __name__ == "__main__":
    tk.mainloop()
