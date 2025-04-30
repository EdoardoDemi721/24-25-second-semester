import re
import os

def get_question_from_file(filename, question_number):
    """
    Extracts a question and its possible answers from a markdown file.

    Args:
        filename (str): The name of the markdown file.
        question_number (int): The number of the question to extract.

    Returns:
        str: The question and its possible answers, or None if not found.
    """

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
            # Found the number, look for the next element which should be the letter
            return elements[i + 1]

import random

def get_random_questions(num_questions, total_questions):
    
    if not isinstance(num_questions, int) or num_questions <= 0 or num_questions > total_questions:
        return []
    return random.sample(range(1, total_questions + 1), num_questions)


def main():
    """
    Main program to get the question number from the user and print the question.
    """

    filename = "Chem quiz.md"
    filepath = os.path.join(os.getcwd(), filename)  # Ensure file is in same directory
    solutions = "1 c 2 d 3 d 4 a 5 a 6 d 7 e 8 b 9 a 10 c 11a 12 c 13 b 14 c 15 b 16 c 17 c 18 d 19 b 20 c 21 c 22 a 23 a 24 b 25 a 26 c 27 a 28 a 29 b 30 b 31 c 32 a 33 e 34 b 35 b 36 c 37 d 38 c 39 a 40 b 41 b 42 d 43 c 44 e 45 c 46 a 47 a 48 c 49 d 50 a 51 a 52 d 53 a 54 e 55 d 56 b 57 d 58 b 59 c 60 a 61 d 62 e 63 a 64 c 65 a 66 c 67 c 68 a 69 a 70 d 71 d 72 c 73 a 74 c 75 d 76 c 77 c 78 d 79 e 80 a 81 d 82 a 83 c 84 e 85 b 86 e 87 c 88 a 89 a 90 a 91 a 92 a 93 e 94 b 95 d 96 b 97 a 98 d 99 a 100 a 101 b 102 a 103 a 104 d 105 e 106 c 107 c 108 a 109 a 110a 111 a 112c 113a 114c 115a 116a 117d 118b 119c 120 a 121 b 122 a 123 a 124 d 125 b 126 b 127 a 128 c 129 d 130 a 131 e 132 c 133 a 134 a 135 c 136 c 137 c 138 e 139 a 140 c 141 a 142 a 143 b 144 d 145 a 146 d 147 a 148 a 149 a 150 d 151 a 152 c 153 b 154 d 155 e 156 a 157 a 158 a 159 b 160 e 161 c 162 a 163 c 164 d 165 d 166 a 167 b 168 c 169 b 170 d 171 a 172 a 173 a 174 c 175 b 176 b 177 b 178 c 179 e 180 b 181 c 182 b 183 d 184 d 185 c 186 b 187 c 188 b 189 e 190 b 191 e 192 b 193 a 194 a 195 e 196 e 197 e 198 c 199 b 200 a 201 b 202 c 203 d 204 d 205 a 206 d 207 e 208 b 209 d 210 a 211b 212 d 213 a 214 e 215 a 216 b 217 d 218 a 219 c 220 d 221 a 222 c 223 d 224 b 225 b 226 a 227 a 228 a 229 a 230 a 231 a 232 a 233 c 234 d 235 c 236 a 237 b 238 b 239 d 240 b 241 b 242 b 243 b 244 e 245 b 246 a 247 a 248 b 249 c 250 d 251 a 252 a 253 a 254 c 255 c 256 a 257 b 258 c 259 c 260 d 261 c 262 c 263 e 264 c 265 b 266 b 267 c 268 c 269 c 270 b 271 a 272 d 273 a 274 e 275 c 276 a 277 a 278 a 279 a 280 c 281 d 282 d 283 a 284 b 285 e 286 a 287 a 288 b 289 c 290 d 291 c 292 a 293 b 294 e 295 a 296 a 297 b 298 e 299 d 300 a 301 a 302 b 303 c 304 a 305 a 306 c 307 b 308 b 309 e 310 a 311a 312 d 313 a 314 e 315 a 316 a 317 a 318 a 319 c 320 c 321 b 322 c 323 a 324 d 325 a 326 c 327 c 328 d 329 a 330 a 331 b 332 a 333 d 334 d 335 c 336 b 337 a 338 b 339 a 340 b 341 c 342 b 343 a 344 e 345 d 346 a 347 e 348 d 349 d 350 d 351 c 352 b 353 c 354 a 355 a 356 d 357 a 358 b 359 d 360 a 361 a 362 b 363 c 364 a 365 c 366 c 367 a 368 a 369 a 370 d 371 a 372 a 373 b 374 a 375 a 376 a 377 c 378 b 379 e 380 d 381 b 382 d 383 c 384 b 385 b 386 b 387 c 388 a 389 a 390 d 391 e 392 c 393 a 394 a 395 a 396 c 397 b 398 c 399 a 400 a 401 b 402 a 403 b 404 a 405 a 406 a 407 c 408 c 409 a 410 e 411b 412 b 413 c 414 c 415 a"

    num_random_questions = 0
    points = 0
    while True:
        try:
            num_random_questions = int(input("Enter the number of random questions you want (0 to exit): "))
            if num_random_questions == 0:
                print("Exiting the program.")
                break
            elif num_random_questions < 0:
                print("Please enter a non-negative number of questions.")
        except ValueError:
            print("Invalid input. Please enter a valid number.")

        if num_random_questions > 0:
            random_question_numbers = get_random_questions(num_random_questions, 415) # Assumendo 415 come numero totale di domande
            if random_question_numbers:
                for q_num in random_question_numbers:
                    selected_question = get_question_from_file(filename, q_num) # 'filename' è una variabile globale
                    if selected_question:
                        print(selected_question)
                        ans = input("What is your answer? \n")
                        sol = find_sol(q_num, solutions) # 'SOLUTIONS' è una variabile globale
                        if sol == ans:
                            print("Correct!")
                            points += 0.6
                        else:
                            print(f"Wrong answer! The correct answer is {sol}.")
                            points-=0.12
                    else:
                        print(f"Question {q_num} not found in '{filename}'.")
                points=points*0.6*15/num_random_questions
                print(f"Your score is: {points} out of 9")
            else:
                print("Invalid number of questions requested.")
   


if __name__ == "__main__":
    main()