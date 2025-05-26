import tkinter as tk

class QuizGUI:
    def __init__(self):
        self.root = tk.Tk()
        self.root.title("Quiz GUI")
        self.result = None

    def ask(self, prompt):
        self.result = None
        window = tk.Toplevel(self.root)
        window.title("Input")

        tk.Label(window, text=prompt).pack(padx=10, pady=5)
        entry = tk.Entry(window)
        entry.pack(padx=10, pady=5)
        entry.focus()

        def submit():
            self.result = entry.get()
            window.destroy()

        tk.Button(window, text="Submit", command=submit).pack(pady=5)
        self.root.wait_window(window)
        return self.result

    def mulChoice(self, domanda, opzioni):
        self.result = None
        window = tk.Toplevel(self.root)
        window.title("Multiple Choice")

        tk.Label(window, text=domanda, wraplength=400).pack(padx=10, pady=5)

        btn_frame = tk.Frame(window)
        btn_frame.pack(pady=5)

        def select(option):
            self.result = option
            window.destroy()

        for opt in opzioni:
            tk.Button(btn_frame, text=opt, command=lambda o=opt: select(o)).pack(side=tk.LEFT, padx=5)

        tk.Button(window, text="Skip", command=lambda: select('z')).pack(pady=5)
        self.root.wait_window(window)
        return self.result

    def display(self, message):
        window = tk.Toplevel(self.root)
        window.title("Message")

        tk.Label(window, text=message, wraplength=400).pack(padx=10, pady=10)

        def next_action():
            window.destroy()

        tk.Button(window, text="Next", command=next_action).pack(pady=5)
        self.root.wait_window(window)

    def start(self):
        self.root.mainloop()
