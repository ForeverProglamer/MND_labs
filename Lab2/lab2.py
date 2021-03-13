from random import randint
import numpy as np


def normalize(array):
    x0 = (max(array) + min(array)) / 2
    dx = x0 - min(array)
    normalized = [(array[i] - x0) / dx for i in range(len(array))]
    return normalized


def main(m):
    var = 127
    y_max = (30 - var) * 10
    y_min = (20 - var) * 10
    print(f'y_max = {y_max}, y_min = {y_min}')

    x1 = [-40, randint(-40, 20), 20]
    x2 = [-25, randint(-25, 10), 10]
    print(f'X1: {x1}')
    print(f'X2: {x2}')

    x1_n = normalize(x1)
    x2_n = normalize(x2)
    print(f'X1 normalized: {x1_n}')
    print(f'X2 normalized: {x2_n}')

    matrix_of_experiments = [[randint(y_min, y_max) for _ in range(3)] for _ in range(m)]
    print(f'Y from 1 to m: {matrix_of_experiments}')

    y_average = []
    dispersions = []
    for i in range(3):
        y_current = (matrix_of_experiments[0][i] + matrix_of_experiments[1][i] +
                     matrix_of_experiments[2][i] + matrix_of_experiments[3][i] +
                     matrix_of_experiments[4][i]) / len(matrix_of_experiments)
        y_average.append(y_current)

        disp = ((matrix_of_experiments[0][i] - y_current) ** 2 + (matrix_of_experiments[1][i] - y_current) ** 2 +
                (matrix_of_experiments[2][i] - y_current) ** 2 + (matrix_of_experiments[3][i] - y_current) ** 2 +
                (matrix_of_experiments[4][i] - y_current) ** 2) / m
        dispersions.append(disp)

    print(f'Y average: {y_average}')
    print(f'Dispersions: {dispersions}')

    main_dispersion = ((2 * (2 * m - 2)) / (m * (m - 4))) ** (1 / 2)
    print(f'Main dispersion = {main_dispersion}')

    f_uv = []
    theta_uv = []
    r_uv = []
    res = 0
    for i in range(3):
        for j in range(i + 1, 3):
            if dispersions[i] >= dispersions[j]:
                res = dispersions[i] / dispersions[j]
            else:
                res = dispersions[j] / dispersions[i]
            f_uv.append(res)

        theta = ((m - 2) / m) * res
        theta_uv.append(theta)

        r_uv.append(abs(theta - 1) / main_dispersion)

    print(f'Fuv : {f_uv}')
    print(f'0uv: {theta_uv}')
    print(f'Ruv: {r_uv}')

    for i in r_uv:
        if i >= 2:
            print('Дисперсії неоднорідні')
            exit()
    print('Дисперсії однорідні')

    mx1 = sum(x1_n) / 3
    mx2 = sum(x2_n) / 3
    my = sum(y_average) / 3

    print(f'mx1: {mx1}')
    print(f'mx2: {mx2}')
    print(f'my: {my}')

    a1 = (x1_n[0] ** 2 + x1_n[1] ** 2 + x1_n[2] ** 2) / 3
    a2 = (x1_n[0] * x2_n[0] + x1_n[1] * x2_n[1] + x1_n[2] * x2_n[2]) / 3
    a3 = (x2_n[0] ** 2 + x2_n[1] ** 2 + x2_n[2] ** 2) / 3
    print(f'a1 = {a1}')
    print(f'a2 = {a2}')
    print(f'a3 = {a3}')

    a11 = (x1_n[0] * y_average[0] + x1_n[1] * y_average[1] + x1_n[2] * y_average[2]) / 3
    a22 = (x2_n[0] * y_average[0] + x2_n[1] * y_average[1] + x2_n[2] * y_average[2]) / 3
    print(f'a11 = {a11}')
    print(f'a22 = {a22}')

    b0 = np.linalg.det([[my, mx1, mx2],
                        [a11, a1, a2],
                        [a22, a2, a3]]) / \
         np.linalg.det([[1, mx1, mx2],
                        [mx1, a1, a2],
                        [mx2, a2, a3]])

    b1 = np.linalg.det([[1, my, mx2],
                        [mx1, a11, a2],
                        [mx2, a22, a3]]) / \
         np.linalg.det([[1, mx1, mx2],
                        [mx1, a1, a2],
                        [mx2, a2, a3]])

    b2 = np.linalg.det([[1, mx1, my],
                        [mx1, a1, a11],
                        [mx2, a2, a22]]) / \
         np.linalg.det([[1, mx1, mx2],
                        [mx1, a1, a2],
                        [mx2, a2, a3]])

    print(f'Нормоване рівняння регресії: \ny = {b0} + {b1}*x1 + {b2}*x2')
    for i in range(3):
        print(f'Для X1{i + 1} та X2{i + 1}')
        print(f'y = {b0 + b1 * x1_n[i] + b2 * x2_n[i]}')

    dx1 = abs(x1[2] - x1[0]) / 2
    dx2 = abs(x2[2] - x2[0]) / 2
    x10 = (x1[2] + x1[0]) / 2
    x20 = (x2[2] + x2[0]) / 2
    print(f'dx1 = {dx1}')
    print(f'dx2 = {dx2}')
    print(f'x10 = {x10}')
    print(f'x20 = {x20}')

    a0_res = b0 - b1 * (x10 / dx1) - b2 * (x20 / dx2)
    a1_res = b1 / dx1
    a2_res = b2 / dx2
    print(f'a0 = {a0_res}')
    print(f'a1 = {a1_res}')
    print(f'a2 = {a2_res}')

    print(f'Натуралізоване рівняння регресії: \ny = {a0_res} + {a1_res}*x1 + {a2_res}*x2')
    for i in range(3):
        print(f'Для X1{i + 1} та X2{i + 1}')
        print(f'y = {a0_res + a1_res * x1[i] + a2_res * x2[i]}')


if __name__ == '__main__':
    main(m=5)
