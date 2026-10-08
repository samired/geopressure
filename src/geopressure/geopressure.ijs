NB. Geopressure analysis rewritten in J with a JQt GUI.

load 'tables/csv'
load 'plot'

readcsvwithheader =: 3 : 0
  raw =. readcsv y
  hdr =. >{.raw
  dat =. }.raw
  hdr ; dat
)

colfrom =: 4 : 0
  'hdr dat' =. y
  idx =. hdr i. <x
  vals =. idx {"1 dat
  > ". each vals
)

calculateDxc =: 3 : 0
  'R N D W MW' =. y
  ((10 ^. (R % (60 * N))) % (10 ^. ((12 * W) % (1000 * D)))) * (MW % 8.4)
)

initdata =: 3 : 0
  dataset =: readcsvwithheader y
  TVD =: 'TVD' colfrom dataset
  ROP =: 'ROP-mhr' colfrom dataset
  RPM =: 'RPM' colfrom dataset
  WOB =: 'WOB-klb' colfrom dataset
  BIT =: 'BIT-in' colfrom dataset
  OB =: 'OBG-gcc' colfrom dataset
  MW =: 'Mud-ppg' colfrom dataset
  QC_DXC =: 'dxc' colfrom dataset
  QC_EMW =: 'EMW-ppg' colfrom dataset
  Dxc =: calculateDxc ROP ; RPM ; BIT ; WOB ; MW
  i.0 0
)

plotScatter =: 3 : 0
  'x y title' =. y
  pd 'reset'
  pd 'type scatter'
  pd 'title ', title
  pd 'x ',":x
  pd 'y ',":y
  pd 'xlabel X'
  pd 'ylabel Y'
  pd 'show'
)

geopressure_btnDxc_button =: 3 : 0
  plotScatter Dxc ; TVD ; 'Dxc vs TVD'
)

geopressure_btnQcDxc_button =: 3 : 0
  plotScatter QC_DXC ; TVD ; 'QC-DXC vs TVD'
)

geopressure_btnQcEmw_button =: 3 : 0
  plotScatter QC_EMW ; TVD ; 'QC-EMW vs TVD'
)

geopressure_btnOb_button =: 3 : 0
  plotScatter OB ; TVD ; 'OB vs TVD'
)

start =: 3 : 0
  initdata 'data/test-data.csv'
  wd 'pc geopressure closeok;'
  wd 'bin v'
  wd 'cc title static; cn "Geopressure plots (JQt)";'
  wd 'cc btnDxc button; cn "Plot Dxc vs TVD";'
  wd 'cc btnQcDxc button; cn "Plot QC-DXC vs TVD";'
  wd 'cc btnQcEmw button; cn "Plot QC-EMW vs TVD";'
  wd 'cc btnOb button; cn "Plot OB vs TVD";'
  wd 'bin z'
  wd 'pshow'
)

start ''
