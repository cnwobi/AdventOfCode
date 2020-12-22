# number 1
def array_merge (students, grades):
    return dict(zip(students,grades))
# number 2
def sum_unique_value (list_number):
    unique = set(list_number)
    return reduce((lambda a, b: a + b), unique)

input = [[7,'Nicole'],[35.2, 'Michael'],[40,'Marya'],[35.2, 'John']]

# number 3
def print_second_lowest_grade(nested_list):
    sorted_nested_list = sorted(nested_list,cmp= lambda x, y: cmp(x[0],y[0]), reverse=True)
    lowest_grade = sorted_nested_list[-1][0]
    nested_list_without_lowest_grade = filter(lambda x: x[0] != lowest_grade,sorted_nested_list)
    second_lowest_grade = nested_list_without_lowest_grade[-1][0]
    student_with_second_lowest_grade = filter(lambda x: x[0] == second_lowest_grade, nested_list_without_lowest_grade)
    return sorted(map(lambda x: x[1], student_with_second_lowest_grade));


print_second_lowest_grade(input)

# number 5
def add_total_covered_area(tuple_list):
    covered_area = 0
    for i in range(len(input)):
        current_tuple = input[i]
        covered_area += current_tuple[1] - current_tuple[0]
        if i > 0 :
            previous_tuple = input[i-1]
            if previous_tuple[1] > current_tuple[0]:
                covered_area -= previous_tuple[1] - current_tuple[0]
    return covered_area



input =  [(0, 1) , (5, 6) , ( 6, 9)]

if __name__ == '__main__':
