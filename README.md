# geopressure

Geopressure analysis rewritten in J with a JQt GUI for plotting DXc and related curves from CSV data.

## Running

1. Install J with JQt support.
2. Launch JQt and load the script:

```
load 'src/geopressure/geopressure.ijs'
```

The GUI provides buttons to render scatter plots for:
- DXc vs TVD
- QC-DXC vs TVD
- QC-EMW vs TVD
- OBG vs TVD

The data source is `data/test-data.csv` by default.
