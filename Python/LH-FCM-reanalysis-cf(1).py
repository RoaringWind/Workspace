# -*- coding: utf-8 -*-
"""
Created on Mon Jun 17 22:19:22 2019

@author: Violet
"""

import pandas as pd
import numpy as np
import math 
extraHeader=['mean Aspect','mean Aspect sd','median Aspect','mean Length','mean Length sd','mean Width','mean Width sd','median Length'
    ,'median Width','mean log(Volume)','mean log(Volume) sd','mean log(GFP)','mean log(GFP) sd','mean log(Area)','mean log(Area) sd'
    ,'mean log(GFP/Volume)','mean log(GFP/Volume) sd','mean log(GFP/Area)','mean log(GFP/Area) sd']
#read to data #读取数据,清洗数据
def ReadData(path):
    d = pd.read_excel(path,sheet_name=0, header=3) #跳过第一行，让第二行作为列名
    df = pd.DataFrame(d)
    
    Sheet_Name = []
    a = pd.read_excel(path,sheet_name=0,header=None)
    Sheet_Name.append(a[a.columns[0]][0].split('\\')[-1])           ############读取表格名
    
    All_df = []                         ##存储分割后的Excel 文件。
    n = 0
#    for i in range(range(len(df))):
    length = len(df)
    i=0
    while(i<length):
        ##########################################遇到一个空行，存储一段
        if math.isnan(df[df.columns[0]][i]):
            temp = df.iloc[n:i]
            All_df.append(temp)
            Sheet_Name.append(df[df.columns[0]][i+1].split('\\')[-1])
            i=i+4
            n=i+1
        i+=1
        ########################################到最后一个空行，存储一次
        if(i>=length):
            temp = df.iloc[n:length]
            All_df.append(temp)
            i=i+4
            n=i+1

#    #按第二列（aspect）排序，删除aspect等于0的行
#    df.sort_values(by=df.columns[2] , ascending=True,inplace = True)
#    df.index = range(len(df))
#    i=0
#    while(df[df.columns[2]][i]==0):
#        df.drop(i,inplace=True)
#        i+=1
#    df.index = range(len(df))
#    
#    for i in range(len(df)):
#        for j in range(len(df.columns)):
#            if(df[df.columns[j]][i])<0:
#                df.drop([i],inplace = True)
#    df.index = range(len(df))
    return All_df,Sheet_Name


#计算所有的数，添加到dataframe ，
def Compute(df):
    volume = []
    log_Volume = []
    log_GFP= []
    log_Area=[]
    log_GFPVolume =[]
    log_GFPArea =[]

    ##Object Number |	Area_M02  |	Aspect RatioIntensity_M02_Ch02  |	Height_M02 |	Intensity_MC_Ch02 | Length_M02 | Width_M02
    for i in range(len(df)):
        try:
            #=PI()/6*F5*G5*G5
#            volume.append(math.pi/6 * df[df.columns[5]][i]*df[df.columns[6]][i]**2)
            volume.append('=PI()/6*F'+str(i+2)+'*G'+str(i+2)+'*G'+str(i+2))
        except:
            volume.append(np.nan)
            
        try:
#            log_Volume.append(math.log10(volume[i]))
            log_Volume.append('=LOG(H'+str(i+2)+')')
        except: 
            log_Volume.append(np.nan)
            
        try:
#            log_GFP.append(math.log10(df[df.columns[4]][i]))
            log_GFP.append('=LOG(E'+str(i+2)+')')
        except:
            log_GFP.append(np.nan)
        
        try:
#            log_Area.append(math.log10(df[df.columns[1]][i]))
            log_Area.append('=LOG(B'+str(i+2)+')')
        except:
            log_Area.append(np.nan)
            
        try:
#            log_GFPVolume.append(math.log10(df[df.columns[4]][i]/volume[i]))
            log_GFPVolume.append('=LOG(E'+str(i+2)+'/H'+str(i+2)+')')
        except:
            log_GFPVolume.append(np.nan)
            
        try:
#            log_GFPArea.append(math.log10(df[df.columns[4]][i]/df[df.columns[1]][i]))
            log_GFPArea.append('=LOG(E'+str(i+2)+'/B'+str(i+2)+')')
        except:
            log_GFPArea.append(np.nan)
        
    df['volume'] = pd.Series(volume)
    df['log_Volume'] = pd.Series(log_Volume)
    df['log_GFP'] = pd.Series(log_GFP)
    df['log_Area'] = pd.Series(log_Area)
    df['log_GFPVolume'] = pd.Series(log_GFPVolume)
    df['log_GFPArea'] = pd.Series(log_GFPArea)
    extraHeader=['mean Aspect','median Aspect','mean Length','mean Width','median Length','median Width','mean log(Volume)','mean log(GFP)','mean log(Area)','mean log(GFP/Volume)'
                 ,'mean log(GFP/Area)','mean Aspect sd','mean Length sd','mean Width sd','mean log(Volume) sd','mean log(GFP) sd','mean log(Area) sd'
                 ,'mean log(GFP/Volume) sd','mean log(GFP/Area) sd']
    dflength=len(df)+1
    extraFormula=[r'=AVERAGE(C2:C%d)'%dflength,r"=MEDIAN(C2:C%d)"%dflength,r"=AVERAGE(F2:F%d)"%dflength,r"=AVERAGE(G2:G%d)"%dflength,r"=MEDIAN(F2:F%d)"%dflength,r"=MEDIAN(G2:G%d)"%dflength,r"=AVERAGE(I2:I%d)"%dflength,r"=AVERAGE(J2:J%d)"%dflength
                  ,r"=AVERAGE(K2:K%d)"%dflength,r"=AVERAGE(L2:L%d)"%dflength,r"=AVERAGE(M2:M%d)"%dflength,r"=STDEVP(C2:C%d)"%dflength,r"=STDEVP(F2:F%d)"%dflength,r"=STDEVP(G2:G%d)"%dflength,r"=STDEVP(I2:I%d)"%dflength,r"=STDEVP(J2:J%d)"%dflength
                  ,r"=STDEVP(K2:K%d)"%dflength,r"=STDEVP(L2:L%d)"%dflength,r"=STDEVP(M2:M%d)"%dflength]
#    df['mean Aspect'] = '=AVERAGE(C'+str(2)+':C'+str(len(df[df.columns[2]])+1)+')'
    for index,head in enumerate(extraHeader):
        df[head]=pd.Series(extraFormula[index])
        #df[head].round(decimals=2)
    return df


OnlyPath = 'C:/Users/luhui/Desktop/All experiment data/'
FileName = 'L3-2-evolutionary data.xlsx'
path = OnlyPath+FileName

#wb =opx.Workbook()
#dest_filename = OnlyPath+'empty_book.xlsx'
#ws1 = wb.active
#
#ws2 = wb.create_sheet(title="Pi")
#
writer = pd.ExcelWriter(OnlyPath+'L3-2-evolutionary data-final.xlsx')

All_df,Sheet_Name = ReadData(path)
shortList=[]
for i in range(len(All_df)): 
    df = All_df[i].reset_index(drop= True)
    dataframe = Compute(df)
    sheetName=Sheet_Name[i][:15]+Sheet_Name[i][-7:-4]
    shortList.append(sheetName)
    dataframe.to_excel(writer, sheet_name = sheetName,index=False)

startOrd=ord("N")
lastFrameList=[]
lastFrame=pd.DataFrame()
print(len(extraHeader))
def go(dig):
    if dig>ord("Z"):
        return "A"+chr(dig-ord("Z")+ord("A")-1)
    else:
        return chr(dig)
lastFrame[0]=pd.Series(Sheet_Name)
for ii,jj in enumerate(extraHeader):
    tmpList=[r"='%s'!%s2"%(shortname,go(startOrd+ii)) for shortname in shortList]
    print(tmpList)
    lastFrame[extraHeader[ii]]=pd.Series(tmpList)
#lastFrameList=list(map(list, zip(*lastFrameList)))
#pd.DataFrame(lastFrameList).to_excel(writer,sheet_name = 'assemble',index_label=["name"]+extraHeader) #超过Z的得变成AA，行列转置，还得加index
lastFrame.to_excel(writer, sheet_name="assemble")
#pd.DataFrame(Sheet_Name).to_excel(writer,sheet_name = 'SheetName')
writer.save()
writer.close()








