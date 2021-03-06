## Linux内核基础知识
#### 关于GCC编译
> 1. Linux内核采用GCC编译器进行编译，配合GDB调试器进行调试。
> 2. GCC编译器（GNU Complier Collection）是Linux操作系统中，默认的编译器，同时也支持多种其他变成语言，例如C++、Java、Go等语言，而且GCC还支持多种不同的硬件平台，例如x86、ARM等体系结构等。
> 3. GCC编译过程主要分4个步骤：预处理 -> 编译 -> 汇编 -> 链接





#### 随手笔记
> Q：用户态到内核态发起的SystemCall其实是不同于普通的函数调用的，普通的函数调用只涉及栈空间的操作，SystemCall呢？
>> A：  

> Q：操作系统从用户态切到内核态是否发生了上下文切换？  
>> A：没有，两者虽然共同之处是在切换前，都会讲各自的上下文保存起来，但用户态陷入内核态对CPU而言仍是同一个进程，只是换了一个执行环境，通俗讲就是当前进程从低特权晋级为高特权；而上下文切换在CPU看来是执行的进程都发生了变化。

> Q：既然可以通过用户态进入内核态，为什么还要区分内核态和用户态？
>> A：首先要明确一点是，区分用户态和内核态不是OS行为，而是CPU，不同厂商的CPU已经对CPU指令进行了特权划分，以Intel为例就划分出了Ring0-Ring3，而Linux和Windows都是只用到了Ring0和Ring3两个级别。  
>> 在内核态下，CPU可以执行任意级别的指令，运行的代码也不受任何限制，其健壮性和正确性也全权依赖于操作系统(毕竟OS的代码还是要健壮的多)；而在用户态下，CPU执行指令时会受到诸多限制，以及检查，可使用的指令也比内核态少了一些。因此可以看出，通过内核态与用户态的隔离，屏蔽了操作系统代码和应用程序的代码，即便单个应用程序出错，对操作系统也没有影响，提高了操作系统的稳定性和可用性。

> A：进程在用户空间运行时用的是用户态的堆栈；在内核空间执行时用到的是内核态的堆栈，也就是说linux进程在同一时刻分别维护了2个栈，一个用户回到用户态，一个用于陷入到内核态。

> A：C语言提供的malloc就是用户态的函数，这个函数对开发者而言，只关心传入大小即可，无需关心在物理内存上的偏移量，碎片等；当进程执行malloc发起SystemCall时，就会调用内核态函数brk或mmap向内核以页为单位申请内存，而这其中的逻辑地址到物理地址的转换是内核态函数需要关心。