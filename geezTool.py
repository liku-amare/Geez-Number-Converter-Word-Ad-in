DIGITS = [
    ["", "፩", "፪", "፫", "፬", "፭", "፮", "፯", "፰", "፱"],
    ["", "፲", "፳", "፴", "፵", "፶", "፷", "፸", "፹", "፺"]
]
HUNDRED = "፻"
TEN_THOUSAND = "፼"

def toGeez(nnum):
    num = nnum.strip()
    converted = []
    num = int(num)
    digit_pos = 1
    while num != 0:
        q = num % 10
        # print(q)
        cur_digit = digit_pos % 4
        if digit_pos > 4 and cur_digit == 1:
            converted.insert(0, TEN_THOUSAND)    # = TEN_THOUSAND + converted
            if q == 1:
                digit_pos += 1
                num = num // 10
                continue
        if cur_digit <= 2:
            converted.insert(0, DIGITS[cur_digit-1][q])     # = DIGITS[cur_digit-1][q] + converted
        else:
            if cur_digit == 3:      # and (num // 10) % 10 != 0
                if q == 1 and num // 10 == 0:
                    converted.insert(0, HUNDRED)    # = DIGITS[cur_digit-3][q] + HUNDRED + converted
                else:
                    converted.insert(0, DIGITS[cur_digit-3][q] + HUNDRED)
            else:
                converted.insert(0, DIGITS[cur_digit-3][q])     # = DIGITS[cur_digit-3][q] + converted
        digit_pos += 1
        num = num // 10
        # print(converted)

    return ''.join(converted)

if __name__ == "__main__":
    while True:
        number = input()
        print(toGeez(number))
        # ፩፼፻
        # 20155
        # ፪፼፩፶፭ ፼፻