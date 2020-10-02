# CPK_Viewer
for process capability analysis
 
# 1. DATA Format

> Data file extension

>extension |
>------------ | 
>.csv | 
>.txt |

> Data file format

>Item1 | Item2 | Item3
>------------ | ------------- | ------------ |
>LSL | LSL | LSL |
>LSU | LSU | LSU |
>DATA | DATA | DATA |
>DATA | DATA | DATA |
>DATA | DATA | DATA |
>DATA | DATA | DATA |

# 2. mathematical expression

```
((USL - LSL) / (6 * STDEV)) - ABS(((LSL + USL) / 2 - AVERAGE) / (3 * STDEV)) 
```

# 3. Library

> XChart : https://knowm.org/open-source/xchart/
