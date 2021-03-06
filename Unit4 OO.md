# BUAA_2022_OO_Unit4 总结

[TOC]

### 一、**总结本单元作业的架构设计**

​      本单元实现了一个UML解析器，第一次作业对**类图**进行建模，实现查询的接口；第二次作业对**顺序图、状态图**进行建模，同样实现查询的接口；第三次作业则对一些**规则**进行检查。

​       官方代码中包括把mdj的输入转化成建模好的UML图中元素，我们为了实现对图的内容的查询指令，需要把单独的UML元素按照UML规定的结构**建立层次关系**，通过不同容器的匹配与盛放，进行指令的查询。

#### 1.1 第一次作业

- 实现过程

  第一次作业是按照UML类图模型对UmlElement建立层次关系，由于mdj文件中类元素的输入顺序是随机的，为了层次的建立需要多次遍历mdj文件，首先记录第一层即模型的最高层，第二三次遍历分别记录在上一层遍历的基础之上。

  ![image-20220627145406501](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220627145406501.png)

- 容器选择

  为了实现parentUMLElement->UMLElement的映射关系，选择HashMap来盛放元素

  ```java
   examples:
  private HashMap<String,UMLElement> id2element = new HashMap<>();
      private HashMap<String, ArrayList<String>> extendList = new HashMap<>();
  ```

#### 2.1 第二次作业

![Main](C:\Users\lenovo\Desktop\Main.png)

-  第二次作业添加了对于顺序图和状态图的建模，我将三种图的建模与指令查询分别放到三个类中，实现了相应图的接口，在`UMLImplementation`中分别调用相应类中的方法进行指令的查询。

-  这样避免了`UMLImplementation`太长，使得在`UMLImplementation`只进行图的调用与指令查询。

#### 1.3 第三次作业

​      本次作业是添加了关于UML图合法性的检查，在相应类图中添加对相应情况的检查即可。

​       本次作业的循环继承，多继承检查我都运用dfs方法，需要注意在循环继承检查时函数返回上一层后对返回路径中相应节点的删除，同时为了防止出现无穷递归的情况，需要在访问过的元素做标记。



### 二、架构设计思维及OO方法理解的演进

#### 2.1 第一单元

- 第一单元实现表达式计算，解析表达式然后建立表达式层次模型，从下到上解决括号展开，计算等问题。

#### 2.2 第二单元

- 第二单元实现电梯系统，主要任务是在理解多线程编程的基础上，实现多线程的运行。重点在于各个线程之间的信息交互、线程安全以及各个线程的任务不受干扰的正确的完成。学习到的方法包括合理的加锁实现互斥，生产者消费者模型，流水线模型等等，深入的学习了多线程实现的方法。

- 多线程的编程模式更是深刻的展现了面向对象编程的思想，依据封装需求对各个线程的类的属性方法进行合理添加，各个对象之间的交互与对资源的争夺，共享…..正确实现多线程的任务并非容易，在bug百出的一个单元也极大的巩固了我对于coding的正确性检验的意识。

#### 2.3 第三单元

- 第三单元实现一个社交网络，理解并运用JML规格。通过JML规格，利用java容器实现性能优良（复杂度小，用时短）的目的，同时在社交网络中涉及到的图的应用与图模型的选取，并将算法封装到社交网络的参与部分的相应类中。在本重点掌握了契约式编程，它规定开发者和使用者之间的规范。

#### 2.4 第四单元

- 实现对UML图的解析，和第一单元层次化结构设计有很多相像之处
- 通过java容器进行UML图中必要结构的解析与储存，主要掌握并巩固了层次化编程的思想，同时在实现UML图的过程中对UML图中元素的含义以及其中的层次关系有了很深入的理解

通过四个单元的训练，我对架构设计思维和面向对象方法在编程上也有了初步的理解。

在OO的每次作业中，我逐渐意识到所谓的代码架构的重要性，一个好的设计才会是一个好的实现，才会有利于后期的迭代开发

关于面向对象思维的理解，我认为在类的构造过程中，对于其类属性与类方法的设计，可以看作是”对象的性质与内部运行规律“；对于多个类的耦合与之间的相互作用可以看作是”对象之间的交互与通信“，通过个体的设计与连接进而完成总体的布局的展开，展现任务的全貌，进行任务的实现。与面向过程式编程，面向对象式编程更加体现以对象为中心，它的封装性、继承性、多态性等等，会在特定的场合表现出其优势。



### 三、四个单元中测试理解与实践的演进

测试在每次作业中都是非常重要的一个环节，我虽然没有自己写测评机，只进行了较为简单的数据对拍和白嫖很多其他同学的测评机（

测试的效果一方面取决于数据的数量，另一方面更多的取决于数据的质量（设计情况的全面，边沿数据的测试等等），即使是同学写的测评机有时也会出现数据范围不够广，没有覆盖较多的情况等问题。因此使用大量数据的广泛测试可以找出一些显而易见或者较为常见的bug，而对于一些边沿的数据引发的bug还是要尽可能去考虑与构造。

在第一单元的测试做的不够到位，代码架构来回重构，细节实现部分缝缝补补，在测试的实践部分只进行了简单的手捏数据测试；在第二单元，构造测试数据是最为复杂的，数据要包括设计不同的请求情况以及不同楼与不同楼层合并作用引发的多种情况，共享数据是否互斥访问等等，参考了别的同学的测评机进行了测试；第三单元难度不是很高，每次作业的难点主要在于复杂度与用时的控制和算法的正确性，采用了大量数据测试与对拍的方法；第四单元同样采用对拍的方法。

这学期在OO上的遗憾就是前两个单元的测试做的不充分，有简单的bug在强侧中显露出来，同时也没有能自己去手动写测评及去测试，留下很多遗憾。



### 四、课程收获

- 了解并加深了对面向对象编程思想的理解
- 增强了任务实现的能力，对于较大代码量的工程也能独立的实现并不断改进性能
- 增加了很多编程思想的学习，比如层次化建模（递归下降），多线程编程，契约式编程…….

### 五、给课程的改进建议

1、个人认为Pre的要求可以更加硬性（防止有些同学十天的任务压缩到最后两天完成，从零到表达式计算还是有些招架不来），可以初步浅引入一下第一单元的递归下降法的讲解，或者加强一下对于git学习的要求，使得第一单元不会过于窘迫  : ( 

2、关于强测，也许可以采用扣分针对bug的个数，我在第三单元因为一个一行代码的bug，导致错了四个点，还是有些痛的。

3、研讨课也许可以做更多的思维扩展与任务开发，仅仅针对作业的分享感觉和课上内容有些重复，好的实现方法会被各组同学反复介绍展示。

​      OO课程终于到达尾声了！完结撒花！在一个学期中能感受到自己些许的进步，从第一单元设计架构时的大脑空白、坐大牢；到后来可以完整高效的设计有利于迭代开发的架构，可以较为熟练的运用各种容器、带入复杂算法……虽然在课程前期有些窘迫、摸爬滚打，但是也在“得道”后尽了自己的努力做到最好。OO的每周无论是上机，作业还是博客，在老师与助教的精心设计之下，给我带来的很大的推动，能让我从零到胸有成竹的完成一项系统性任务，同时也建立了很多宝贵的编程思想。

​      感谢各位老师、助教与小伙伴的帮助！希望OO课程越来越好！