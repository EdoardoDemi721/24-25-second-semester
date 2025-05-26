import tkinter as tk
import random
import re
import os

filename = "Chem quiz.md"
filepath = os.path.join(os.getcwd(), filename)
solutions = "1 c 2 d 3 d 4 a 5 a 6 d 7 e 8 b 9 a 10 c 11 a 12 c 13 b 14 c 15 b 16 c 17 c 18 d 19 b 20 c"

def get_question_from_file(filename, question_number):
    try:
        with open(filename, 'r', encoding='utf-8') as file:
            text = file.read()
    except FileNotFoundError:
        return None

    start_pattern = rf"{question_number}\. "
    start_match = re.search(start_pattern, text)
    if not start_match:
        return None

    start_index = start_match.start()
    end_index = len(text)

    next_question_number = question_number + 1
    end_pattern = rf"{next_question_number}\. "
    end_match = re.search(end_pattern, text)
    if end_match:
        end_index = end_match.start()

    return text[start_index:end_index].strip()

def find_sol(number_to_find, formatted_string):
    elements = formatted_string.split()
    for i, element in enumerate(elements):
        try:
            number = int(element)
        except ValueError:
            continue
        if number == int(number_to_find):
            return elements[i + 1]

def get_random_questions(num_questions, total_questions):
    if not isinstance(num_questions, int) or num_questions <= 0 or num_questions > total_questions:
        return []
    return random.sample(range(1, total_questions + 1), num_questions)

class QuizApp:
    def __init__(self, master):
        self.master = master
        master.title("Chem Quiz")

        self.question_label = tk.Label(master, text="Premi Start per cominciare!", wraplength=500, justify="left")
        self.question_label.pack(pady=10)

        self.button_frame = tk.Frame(master)
        self.button_frame.pack()

        self.start_button = tk.Button(master, text="Start Quiz", command=self.start_quiz)
        self.start_button.pack(pady=10)

        self.points = 0
        self.current_question_index = 0
        self.random_questions = []

    def start_quiz(self):
        self.points = 0
        self.random_questions = get_random_questions(5, 20)  # Metti 415 per tutte le domande
        self.current_question_index = 0
        self.show_question()

    def show_question(self):
        if self.current_question_index >= len(self.random_questions):
            self.show_score()
            return

        q_num = self.random_questions[self.current_question_index]
        question_text = get_question_from_file(filename, q_num)

        if not question_text:
            self.question_label.config(text=f"Domanda {q_num} non trovata.")
            return

        self.current_q_num = q_num
        self.current_solution = find_sol(q_num, solutions)

        self.question_label.config(text=question_text)

        # Clear previous buttons
        for widget in self.button_frame.winfo_children():
            widget.destroy()

        for option in ['a', 'b', 'c', 'd', 'e']:
            btn = tk.Button(self.button_frame, text=option, width=10, command=lambda opt=option: self.check_answer(opt))
            btn.pack(side=tk.LEFT, padx=5)

    def check_answer(self, selected):
        if selected == self.current_solution:
            self.points += 0.6
        else:
            self.points -= 0.12

        self.current_question_index += 1
        self.show_question()

    def show_score(self):
        final_score = self.points * 0.6 * 15 / len(self.random_questions)
        self.question_label.config(text=f"Quiz finito! Il tuo punteggio è: {final_score:.2f} su 9")

        for widget in self.button_frame.winfo_children():
            widget.destroy()

        restart_btn = tk.Button(self.button_frame, text="Ricomincia", command=self.start_quiz)
        restart_btn.pack()

if __name__ == "__main__":
    root = tk.Tk()
    app = QuizApp(root)
    root.mainloop()
