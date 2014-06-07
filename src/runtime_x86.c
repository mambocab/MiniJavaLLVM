#include <stdio.h>
#include <stdlib.h>

extern void Main_main();
extern void MJCStatic_init();

void System_out(int x) {
  printf("%d\n", x);
}

#define HEAPLEN (256 * 1024 * 1024)
int freeHeap = 0;
int heap[HEAPLEN];

void MJC_globalRoot(void *root) {
  // not used as no gc for x86
  return;
}

int* MJC_allocObject(int size) {
  int* newObj = heap + freeHeap;
  if (size + freeHeap >= HEAPLEN) {
    fprintf(stderr, "Out of memory!");
    exit(1);
  }
  freeHeap += size;
  return newObj;
}

void MJC_die() {
  exit(-1);
}

int* MJC_allocArray(int elementSize, int len) {
  int size = elementSize * len;
  int* newArr = heap + freeHeap;
  if (size + freeHeap >= HEAPLEN) {
    fprintf(stderr, "Out of memory!");
    exit(1);
  }
  freeHeap += size;
  return newArr;

}

void MJC_putc(char c) {
  printf("%c", c);
}
int main() {
  //    printf("Starting:\n");
  MJCStatic_init();
  Main_main();
  //    printf("Finishing (%d words allocated).\n",freeHeap);
  return 0;
}

