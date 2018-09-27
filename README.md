# Packager Challenge
Author: Kwaku Twumasi-Afriyie <kwaku.twumasi@quakearts.com>

A packager that accepts a test input file containing a list of items with index, weight and cost and a maximum weight a package can take. It selects the items that have a total weight less than the maximum weight and the highest total cost.

##### Requirements
* Java 8+

##### Building
###### Maven

###### Gradle

###### Using the JAR file

##### Repository Structure

##### Design Notes

This problem is a partition and a decision problem. The task requires that first the items be partitioned into groups whose total weight is less than the maximum weight. Then each group needs to be compared, and the group with the highest cost should be returned.
<br />
Decision and partition problems are best handled using tree structures.
<br />
The partitioning can be modeled using a binary tree where each level of the tree represents the selection of the corresponding items into one of two groups. The full partition tree will be of height N+1 where N is the number items to partition, and will have 2<sup>N+1</sup> leaf nodes representing 2<sup>N+1</sup> different ways of partitioning N items.
<br />
In order to select the best choice we need to examine the partitions that first meet the criteria of having a weight less than the target weight, then we need to compare each candidate group and select the group with the highest cost. This would mean executing an exhaustive search of the tree. 
<br />
An exhaustive search can result in long run times. We can optimize search by keeping track of the best selection found and replacing it with a better selection if one is encountered. In addition, we will not need to construct the entire binary tree before searching it; we can optimize further by performing the search whiles constructing the tree. These optimizations will speed up processing. Since this is a challenge task, to keep the code simple and readable, recursion will be used to construct the tree. 
<br />
Since the implementation is in Java, there is the possibility of exhausting the call stack if the input is quite large. Even a list of 30 items will result in 2<sup>31</sup> nodes which is 2,147,483,648 possible operations. For large input sets, it will be optimal to remove the recursive calls, and utilize permanent storage to hold current best selection. It would mean a longer run time, however since decision and partition problems require exhaustive search to find exact matches, there is no other way. If the implementation required an approximation, heuristics can be used to find an approximation of the best choice.
