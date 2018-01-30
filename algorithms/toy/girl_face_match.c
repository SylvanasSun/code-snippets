#include <stdio.h>
#include <math.h>

// From movie <<The Social Network>>
// This algorithm is very naive so not applicable to engineering environment

typedef struct {
    const char *name;
    int rank;
    double expect_rate;
} girl;

const int K = 10;

void read_girl(girl g) {
    printf("Girl name: %s, rank: %d, expect_rate: %.5f\n",g.name,g.rank,g.expect_rate);
}

void compute_expect_rate(girl *a,girl *b) {
    int a_rank = a->rank;
    int b_rank = b->rank;
    // expect rate formula
    // Ea = 1 / (1 + 10 ^ ((Rb-Ra) / 400))
    double a_rank_differ = (double) (b_rank - a_rank) / 400;
    double a_rank_rate = pow(10,a_rank_differ);
    a->expect_rate = 1 / (1 + a_rank_rate);
    // Eb = 1 / (1 + 10 ^ ((Ra-Rb) / 400))
    double b_rank_differ = (double) (a_rank - b_rank) / 400;
    double b_rank_rate = pow(10,b_rank_differ);
    b->expect_rate = 1 / (1 + b_rank_rate);
}

// new rank formula: Na = Oa + K(W - Ea)
void compute_rank(girl *a,girl *b,int a_win_rate,int b_win_rate) {
    a->rank = a->rank + K * (a_win_rate - a->expect_rate);
    b->rank = b->rank + K * (b_win_rate - b->expect_rate);
}

int main(int argc,char *argv[]) {
    char a_girl_name[20];
    char b_girl_name[20];

    girl a = {.name = "A Gril",.rank = 1400};
    girl b = {.name = "B Gril",.rank = 1400};

    compute_expect_rate(&a,&b);

    read_girl(a);
    read_girl(b);

    while (1) {
        char choice[2];
        printf("Choice A or B?\n");
        scanf("%s",choice);
        if (choice[0] == 'A') {
            compute_rank(&a,&b,1,0);
            compute_expect_rate(&a,&b);
        } else if (choice[0] == 'B') {
            compute_rank(&a,&b,0,1);
            compute_expect_rate(&a,&b);
        } else {
            printf("Invalid choice!\n");
            break;
        }
        read_girl(a);
        read_girl(b);
    }
}
