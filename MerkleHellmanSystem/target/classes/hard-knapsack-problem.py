from liblll import *
import sys

the_sum = int(sys.argv[1])
pubkey=[]
for j in range(2,len(sys.argv)):
	pubkey.append(int(sys.argv[j]))

mat = create_matrix_from_knapsack(pubkey, the_sum)
mat_reduced = lll_reduction(mat)

best_vect = best_vect_knapsack(mat_reduced)

# try complementary ?
apply_complementary = True
for i in range(len(best_vect)):
    if best_vect[i] != 0:
        apply_complementary = False

if apply_complementary:
    total_sum = 0
    for i in range(len(pubkey)):
        total_sum += pubkey[i]

    mat = create_matrix_from_knapsack(pubkey, total_sum-the_sum)
    mat_reduced = lll_reduction(mat)

    best_vect = best_vect_knapsack(mat_reduced)

    my_sum = 0
    for i in range(len(pubkey)):
        if best_vect[i] == 0:
            my_sum += pubkey[i]
    

else:
    my_sum = 0
    for i in range(len(pubkey)):
        if best_vect[i] == 1:
            my_sum += pubkey[i]
    

print(best_vect)
