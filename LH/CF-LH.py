import pandas as pd
import numpy as np
from datetime import time
import openpyxl
import shutil 
from os import path

sourcePath=r"D:\200828-L3-1+L11-1-100+Glu.xlsx"
finalPath=path.join(sourcePath[:sourcePath.rfind("\\")],"[result]"+sourcePath.split("\\")[-1])

shutil.copyfile(sourcePath,finalPath)
file=pd.read_excel(finalPath,sheet_name=0,dtype=str)
firstName=file.iloc[71,0]
secondName=file.iloc[172,0]
file1=pd.read_excel(finalPath,sheet_name=0,header=74)
f1_header=list(file1)
A2_A11=[1]+list(range(4,14))
A_H_1_A_H_12=[1]+[3+x*12 for x in range(8)]+[14+x*12 for x in range(8)]
BCD_2_11=[1]
EFG_2_11=[1]
tmp=[16,28,40,21,33,45]
for x in range(5):
    BCD_2_11+=[i+x for i in tmp]
    EFG_2_11+=[i+x+36 for i in tmp]
handleList=[A2_A11,A_H_1_A_H_12,BCD_2_11,EFG_2_11]
for j in range(98):
    if file1.iat[j,1]==time(0) or j==97:
        break
for jj in range(101,199):
    if file1.iat[jj,1]==time(0) or jj==198:
        break
finalDataFrameNameList=["ddH2O","M63#","L3-2-1-100","L3-2-1-1000"]
finalDataFrameList=[]
for index,handleHeader in enumerate(handleList):
    df1=file1.iloc[:j,handleHeader]
    df2=file1.iloc[101:jj,handleHeader]
    t=np.array([0.5*x for x in range(j)])
    df1["时间"]=t
    df2["时间"]=t
    if index==2:
        df1=df1.rename(columns={'时间':'1-100'})
        df2=df2.rename(columns={'时间':'1-100'})
    if index==3:
        df1=df1.rename(columns={'时间':'1-1000'})
        df2=df2.rename(columns={'时间':'1-1000'})
    workDF=pd.DataFrame(columns=df1.columns)
    s=pd.Series([""]*len(handleHeader),index=df1.columns)
    s1=pd.Series([firstName]+[""]*(len(handleHeader)-1),index=df1.columns)
    s2=pd.Series([secondName]+[""]*(len(handleHeader)-1),index=df1.columns)
   
    if index>1:
        s3_4=pd.Series(["L3-2"]+["10.5mM"]*6+["2.1mM"]*6+["1.05mM"]*6+["0.21mM"]*6+["0.105mM"]*6,index=df1.columns)
        workDF=workDF.append(s3_4,ignore_index=True)
    workDF=workDF.append(s1,ignore_index=True)
    df1=df1.append(s2,ignore_index=True)
    res=pd.concat([workDF,df1,df2])
    finalDataFrameList.append(res)
    

writer = pd.ExcelWriter(finalPath,engine='openpyxl', mode='a')
# OD_start,EGFP_start=74,175
# file.iloc[OD_start:OD_start+97,1]=[x*0.5 for x in range(97)]
# file.iloc[EGFP_start:EGFP_start+97,1]=[x*0.5 for x in range(97)]
# file.to_excel(writer,sheet_name="Source",index=False,header=False)
for name,DF in zip(finalDataFrameNameList,finalDataFrameList):
    DF.to_excel(writer,sheet_name=name,index=False)
writer.save()
writer.close()