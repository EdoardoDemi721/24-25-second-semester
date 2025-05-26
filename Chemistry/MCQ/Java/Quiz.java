import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.*;

public class Quiz {

    // Legge tutto il file in una stringa
    public static String readFile(String filename) {
        try {
            return new String(Files.readAllBytes(Paths.get(filename)));
        } catch (IOException e) {
            return null;
        }
    }

    // Estrae la domanda dal file markdown
    public static String getQuestionFromFile(String text, int questionNumber) {
        if (text == null) return null;

        // Pattern "n. " per inizio domanda
        String startPattern = questionNumber + "\\. ";
        Pattern startPat = Pattern.compile(startPattern);
        Matcher startMatcher = startPat.matcher(text);

        if (!startMatcher.find()) return null;
        int startIndex = startMatcher.start();

        int endIndex = text.length();

        // Cerca domanda successiva per delimitare il testo
        int nextQuestionNumber = questionNumber + 1;
        String endPattern = nextQuestionNumber + "\\. ";
        Pattern endPat = Pattern.compile(endPattern);
        Matcher endMatcher = endPat.matcher(text);

        if (endMatcher.find(startIndex + 1)) {
            endIndex = endMatcher.start();
        }

        return text.substring(startIndex, endIndex).trim();
    }

    // Trova la soluzione corrispondente al numero domanda nel formato "1 c 2 d ..."
    public static String findSolution(int numberToFind, String formattedString) {
        String[] elements = formattedString.split("\\s+");
        for (int i = 0; i < elements.length - 1; i++) {
            try {
                int num = Integer.parseInt(elements[i]);
                if (num == numberToFind) {
                    return elements[i + 1];
                }
            } catch (NumberFormatException ignored) {
            }
        }
        return null;
    }

    // Genera una lista di numeri random unici tra 1 e totalQuestions inclusi
    public static List<Integer> getRandomQuestions(int numQuestions, int totalQuestions) {
        if (numQuestions <= 0 || numQuestions > totalQuestions) return Collections.emptyList();

        List<Integer> allQuestions = new ArrayList<>();
        for (int i = 1; i <= totalQuestions; i++) allQuestions.add(i);

        Collections.shuffle(allQuestions);
        return allQuestions.subList(0, numQuestions);
    }

    // Metodo principale
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String filename = "Chem quiz.md";

        // Legge il file una volta sola
        String fileContent = readFile(filename);

        MyQuizGui gui = new MyQuizGui();

        // Stringa soluzioni
        String solutions = 
        "1 c 2 d 3 d 4 a 5 a 6 d 7 e 8 b 9 a 10 c 11a 12 c 13 b 14 c 15 b 16 c 17 c 18 d 19 b 20 c " +
        "21 c 22 a 23 a 24 b 25 a 26 c 27 a 28 a 29 b 30 b 31 c 32 a 33 e 34 b 35 b 36 c 37 d 38 c 39 a 40 b " +
        "41 b 42 d 43 c 44 e 45 c 46 a 47 a 48 c 49 d 50 a 51 a 52 d 53 a 54 e 55 d 56 b 57 d 58 b 59 c 60 a " +
        "61 d 62 e 63 a 64 c 65 a 66 c 67 c 68 a 69 a 70 d 71 d 72 c 73 a 74 c 75 d 76 c 77 c 78 d 79 e 80 a " +
        "81 d 82 a 83 c 84 e 85 b 86 e 87 c 88 a 89 a 90 a 91 a 92 a 93 e 94 b 95 d 96 b 97 a 98 d 99 a 100 a " +
        "101 b 102 a 103 a 104 d 105 e 106 c 107 c 108 a 109 a 110a 111 a 112c 113a 114c 115a 116a 117d 118b 119c 120 a " +
        "121 b 122 a 123 a 124 d 125 b 126 b 127 a 128 c 129 d 130 a 131 e 132 c 133 a 134 a 135 c 136 c 137 c 138 e 139 a 140 c " +
        "141 a 142 a 143 b 144 d 145 a 146 d 147 a 148 a 149 a 150 d 151 a 152 c 153 b 154 d 155 e 156 a 157 a 158 a 159 b 160 e " +
        "161 c 162 a 163 c 164 d 165 d 166 a 167 b 168 c 169 b 170 d 171 a 172 a 173 a 174 c 175 b 176 b 177 b 178 c 179 e 180 b " +
        "181 c 182 b 183 d 184 d 185 c 186 b 187 c 188 b 189 e 190 b 191 e 192 b 193 a 194 a 195 e 196 e 197 e 198 c 199 b 200 a " +
        "201 b 202 c 203 d 204 d 205 a 206 d 207 e 208 b 209 d 210 a 211b 212 d 213 a 214 e 215 a 216 b 217 d 218 a 219 c 220 d " +
        "221 a 222 c 223 d 224 b 225 b 226 a 227 a 228 a 229 a 230 a 231 a 232 a 233 c 234 d 235 c 236 a 237 b 238 b 239 d 240 b " +
        "241 b 242 b 243 b 244 e 245 b 246 a 247 a 248 b 249 c 250 d 251 a 252 a 253 a 254 c 255 c 256 a 257 b 258 c 259 c 260 d " +
        "261 c 262 c 263 e 264 c 265 b 266 b 267 c 268 c 269 c 270 b 271 a 272 d 273 a 274 e 275 c 276 a 277 a 278 a 279 a 280 c " +
        "281 d 282 d 283 a 284 b 285 e 286 a 287 a 288 b 289 c 290 d 291 c 292 a 293 b 294 e 295 a 296 a 297 b 298 e 299 d 300 a " +
        "301 a 302 b 303 c 304 a 305 a 306 c 307 b 308 b 309 e 310 a 311a 312 d 313 a 314 e 315 a 316 a 317 a 318 a 319 c 320 c " +
        "321 b 322 c 323 a 324 d 325 a 326 c 327 c 328 d 329 a 330 a 331 b 332 a 333 d 334 d 335 c 336 b 337 a 338 b 339 a 340 b " +
        "341 c 342 b 343 a 344 e 345 d 346 a 347 e 348 d 349 d 350 d 351 c 352 b 353 c 354 a 355 a 356 d 357 a 358 b 359 d 360 a " +
        "361 a 362 b 363 c 364 a 365 c 366 c 367 a 368 a 369 a 370 d 371 a 372 a 373 b 374 a 375 a 376 a 377 c 378 b 379 e 380 d " +
        "381 b 382 d 383 c 384 b 385 b 386 b 387 c 388 a 389 a 390 d 391 e 392 c 393 a 394 a 395 a 396 c 397 b 398 c 399 a 400 a " +
        "401 b 402 a 403 b 404 a 405 a 406 a 407 c 408 c 409 a 410 e 411b 412 b 413 c 414 c 415 a";


        double points = 0;

        while (true) {
            String input = gui.ask("Enter the number of random questions you want (0 to exit): ");

            int numRandomQuestions;
            try {
                numRandomQuestions = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                gui.display("Invalid input. Please enter a valid number.");
                continue;
            }

            if (numRandomQuestions == 0) {
                gui.display("Exiting the program.");
                break;
            }

            if (numRandomQuestions < 0) {
                gui.display("Please enter a non-negative number of questions.");
                continue;
            }

            List<Integer> randomQuestions = getRandomQuestions(numRandomQuestions, 415);
            if (randomQuestions.isEmpty()) {
                gui.display("Invalid number of questions requested.");
                continue;
            }

            for (int qNum : randomQuestions) {
                String question = getQuestionFromFile(fileContent, qNum);
                if (question == null) {
                    gui.display("Question " + qNum + " not found in '" + filename + "'.");
                    continue;
                }
                
                String ans = gui.multiChoice(question + "\nWhat is your answer? \n", new String[]{"a", "b", "c", "d", "e"});

                String sol = findSolution(qNum, solutions);
                if (sol != null && sol.equalsIgnoreCase(ans)) {
                    gui.display("Correct!");
                    points += 0.6;
                } 
                else if (sol != null && sol.equalsIgnoreCase("z")) {
                    points+=0;
                }
                else {
                    gui.display("Wrong answer! The correct answer is " + sol + ".");
                    points -= 0.12;
                }
            }

            points = points * 0.6 * 15 / numRandomQuestions;
            gui.display(String.format("Your score is: %.2f out of 9", points));
        }
    }
}
