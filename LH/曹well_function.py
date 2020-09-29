# -*- coding: utf-8 -*-
"""
Created on Tue Nov 20 12:08:18 2018

@author: Violet
"""

import pandas as pd
import xlwt
import matplotlib.pyplot as plt
import numpy as np
import math as math
from scipy.optimize import curve_fit

########输入地址，返回data ##########
def Read_OriginalForm(path):
    data = pd.read_excel(path)
    return data

######## 输入data数组，返回所有OD数据的数组
def allData(path):
    data = Read_OriginalForm(path)
    each_well =[["A1"],["A2"],["A3"],["A4"],["A5"],["A6"],["A7"],["A8"],["A9"],["A10"],["A11"],["A12"],
                ["B1"],["B2"],["B3"],["B4"],["B5"],["B6"],["B7"],["B8"],["B9"],["B10"],["B11"],["B12"],
                ["C1"],["C2"],["C3"],["C4"],["C5"],["C6"],["C7"],["C8"],["C9"],["C10"],["C11"],["C12"],
                ["D1"],["D2"],["D3"],["D4"],["D5"],["D6"],["D7"],["D8"],["D9"],["D10"],["D11"],["D12"],
                ["E1"],["E2"],["E3"],["E4"],["E5"],["E6"],["E7"],["E8"],["E9"],["E10"],["E11"],["E12"],
                ["F1"],["F2"],["F3"],["F4"],["F5"],["F6"],["F7"],["F8"],["F9"],["F10"],["F11"],["F12"],
                ["G1"],["G2"],["G3"],["G4"],["G5"],["G6"],["G7"],["G8"],["G9"],["G10"],["G11"],["G12"],
                ["H1"],["H2"],["H3"],["H4"],["H5"],["H6"],["H7"],["H8"],["H9"],["H10"],["H11"],["H12"]]
    i = 1                                                               #    循环退出条件
    cycle = data[data.columns[1]][70]                                   #第一个循环
    while i == cycle:
        for j in range(8):                    #8 row
            for k in range(12):               #12 columns
                each_well[j*12+k].append(data[data.columns[k+1]][72+(j)+(i-1)*13])
        
        #print("Cycle:"+str(cycle))
        cycle = data[data.columns[1]][70+13*i]                          #第i个循环
        i+=1
    
    return each_well

######### 按列输出所有数据的数组，每一个孔的，######
def Write_allData(path,onlyPath):
    each_well = allData(path)                           #####获得所有数据的数组
    
    allwell = xlwt.Workbook()                                          #新建工作簿，
    work_sheet= allwell.add_sheet("all_data")                          #新建一个表格
    ###################写入X轴时间##################################
    work_sheet.write(0,0,'time(0.5h)')                  #第一列写入时间
    for i in range(len(each_well[0])-1):
        work_sheet.write(i+1,0,i*0.5)
    
    #####################################写入数据###################
    for i in range(len(each_well)):
        for j in range(len(each_well[0])):
            work_sheet.write(j,i+1,each_well[i][j])
    
    allwell.save(onlyPath+'all_data.xls')
    return
    
##############数据按孔分类##############
def lrAndUn_Blank(path,onlyPath):
    each_well = allData(path)           #返回所有数据的按列存储的数组
    
    lr_colunms = xlwt.Workbook()
    ud_rows = xlwt.Workbook()
    
    lr_sheet = lr_colunms.add_sheet('左右空白')             #申请表格
    ud_sheet = ud_rows.add_sheet('上下空白')
    lr= ['A1','B1','C1','D1','E1','F1','G1','H1',
         'A12','B12','C12','D12','E12','F12','G12','H12',]
    lr_num= [0,12,24,36,48,60,72,84,
         11,23,35,47,59,71,83,95]
    ud= ['A2','A3','A4','A5','A6','A7','A8','A9','A10','A11',
         'H2','H3','H4','H5','H6','H7','H8','H9','H10','H11',]
    ud_num= [1,2,3,4,5,6,7,8,9,10,
         85,86,87,88,89,90,91,92,93,94]

    ##########################写入X轴坐标（时间）###################
    lr_sheet.write(0,0,'time(0.5h)')
    ud_sheet.write(0,0,'time(0.5h)')
    for i in range(len(each_well[0])-1):
        lr_sheet.write(i+1,0,i*0.5)
        ud_sheet.write(i+1,0,i*0.5)

    ############################写入上下空白，左右空白，并且输出##########
    blank_lr = 0            #存左右blank的平均值   
    for i in range(len(lr_num)):                                ##共i列空白
        for j in range(len(each_well[0])):
            lr_sheet.write(j,i+1,each_well[lr_num[i]][j])
            if(j!=0):
                blank_lr+=each_well[lr_num[i]][j]
    
    blank_lr= blank_lr/(i*(j-1))

    ##########################上下空白
    blank_ud = 0            #存上下blank 的平均值
    for i in range(len(ud_num)):
        for j in range(len(each_well[0])):
            ud_sheet.write(j,i+1,each_well[ud_num[i]][j])
            if j!=0:
                blank_ud+=each_well[ud_num[i]][j]
    blank_ud= blank_ud/(i*(j-1))

    lr_colunms.save(onlyPath+'left&right.xls')
    ud_rows.save(onlyPath+'up&down.xls')

    return blank_lr, blank_ud


################输入地址，输出中间部分实验数据
def CentreData(path,Onlypath,blank):
    each_well = allData(path)           #返回所有数据的按列存储的数组
    used= ["B2","B3","B4","B5","B6","B7","B8","B9","B10","B11",
           "C2","C3","C4","C5","C6","C7","C8","C9","C10","C11",
           "D2","D3","D4","D5","D6","D7","D8","D9","D10","D11",
           "E2","E3","E4","E5","E6","E7","E8","E9","E10","E11",
           "F2","F3","F4","F5","F6","F7","F8","F9","F10","F11",
           "G2","G3","G4","G5","G6","G7","G8","G9","G10","G11",]
    
    used_num = ['13','14','15','16','17','18','19','20','21','22',
                '25','26','27','28','29','30','31','32','33','34',
                '37','38','39','40','41','42','43','44','45','46',
                '49','50','51','52','53','54','55','56','57','58',
                '61','62','63','64','65','66','67','68','69','70',
                '73','74','75','76','77','78','79','80','81','82']
    
    used_Order = ['B2','C2','D2','B7','C7','D7',
                  'B3','C3','D3','B8','C8','D8',
                  'B4','C4','D4','B9','C9','D9',
                  'B5','C5','D5','B10','C10','D10',
                  'B6','C6','D6','B11','C11','D11',
                  'E2','F2','G2','E7','F7','G7',
                  'E3','F3','G3','E8','F8','G8',
                  'E4','F4','G4','E9','F9','G9',
                  'E5','F5','G5','E10','F10','G10',
                  'E6','F6','G6','E11','F11','G11']
    
    used_Order_num = ['13','25','37','18','30','42',
                '14','26','38','19','31','43',
                '15','27','39','20','32','44',
                '16','28','40','21','33','45',
                '17','29','41','22','34','46',
                '49','61','73','54','66','78',
                '50','62','74','55','67','79',
                '51','63','75','56','68','80',
                '52','64','76','57','69','81',
                '53','65','77','58','70','82']
    
    used_Order_num = [13,25,37,18,30,42,
                      14,26,38,19,31,43,
                      15,27,39,20,32,44,
                      16,28,40,21,33,45,
                      17,29,41,22,34,46,
                      49,61,73,54,66,78,
                      50,62,74,55,67,79,
                      51,63,75,56,68,80,
                      52,64,76,57,69,81,
                      53,65,77,58,70,82]
    
    udArray = [[] for i in range(len(used_Order_num))]               #保存有用的数据
    
    used_data = xlwt.Workbook()
    used_sheet = used_data.add_sheet('used数据')
    ###################写入第一列，x轴数据###############3
    used_sheet.write(0,0,'time(0.5h)')
    
    for i in range(len(each_well[0])):
        used_sheet.write(i+1,0,i*0.5)
    
    #####################读取数组内数据，写入表格###########
    for i in range(len(used_Order_num)):
        for j in range(len(each_well[0])):
            if j==0:
                used_sheet.write(j,i+1,each_well[used_Order_num[i]][j])
                udArray[i].append(each_well[used_Order_num[i]][j])
            else:
                used_sheet.write(j,i+1,each_well[used_Order_num[i]][j])
                udArray[i].append(each_well[used_Order_num[i]][j])
                
    used_data.save(Onlypath+'usedData.xls')
    return udArray              #用数组 返回中间数据

    
#绘制所有孔的图像
def Drow_96Well(path,OnlyPath):
    ALLDataArray = allData(path)    #获得所有数据的数组
    
    xdata = []
    for i in range(len(ALLDataArray[0])-1):
        xdata.append(i*0.5)
        
    plt.figure(figsize=(42,24))
    for i in range(8):
        for j in range(12):
            ydata = ALLDataArray[j+i*12]
            ydata.pop(0)
            plt.subplot(8,12,j+i*12+1)
            plt.scatter(xdata,ydata,c='r',s=2)
            #plt.axis([0,28,0,1.5])
            #设置坐标轴 长度为时间+2
            plt.axis([0,len(xdata)/2+2,-0.1,1.5])
            plt.grid()
# =============================================================================
#             plt.axis([0,len(udArray[0])/2+2,7e-2,15e-1]) ##log
#             plt.yscale('log')
# =============================================================================
    plt.margins(0,0)
    
    plt.savefig(OnlyPath+'all_well.jpg')
    plt.show()
    
    return

#绘制中间部分数据的函数图像
def Drow_ContreData(path,OnlyPath,blank):
    udArray = CentreData(path,OnlyPath,blank)
    
    xdata = []
    for i in range(len(udArray[0])-1):
        xdata.append(i*0.5)
    
    plt.figure(figsize=(21,30))
    for i in range(len(udArray)):
        udArray[i].pop(0)
        ydata = udArray[i] - blank
        plt.subplot(10,6,i+1)
        plt.scatter(xdata,ydata,c = 'r',s =2)
        plt.axis([0,len(udArray[0])/2+2,-0.1,1.5])
        plt.grid()
# =============================================================================
#         plt.axis([0,len(udArray[0])/2+2,1e-4,15e-1]) ##log
#         plt.yscale('log')
# =============================================================================
        
    plt.savefig(OnlyPath+'contre.jpg')
    plt.show()

    return udArray

#拟合使用的函数
def func(t, K, N0, r):  
    return K / (1 + ( (K - N0) / N0) * np.exp(-r * t)) #Logistic
    #return K * np.exp(-N0*np.exp(-r*t))                 #Gompertz
    
###绘制拟合后的图
def DataFitting(path,OnlyPath):

    w = xlwt.Workbook()  #创建一个工作簿
    ws = w.add_sheet('Para')  #创建一个工作表

    ws.write(0,0,'Sheet')
    ws.write(0,1,'K') #在1行1列写入bit
    ws.write(0,2,'N0') #在1行2列写入huang
    ws.write(0,3,'r') #在1行3列写入xuan
    
    plt.figure(figsize=(21,30))             #图片大小
    #plt.figure(figsize=(9,8))             #图片大小 分表时
    summ = 0

    list_K = []
    list_N0 = []
    list_r = []
        
    data = pd.read_excel(path,0)                #第o个表格
    #修改过 减了一,,,,忘了为什么？？？
    
# =============================================================================
#       plt.subplot 用的
#     COL = len(data.columns)-1                     ##列数
#     Pic_col = math.sqrt(COL)                          ##  开方
#     Pic_col = math.ceil(Pic_col)                            ## 向上取整
#     Pic_col = 7    ##特殊情况设定为特定值 
# =============================================================================

    plt.suptitle('Sheet-'+str(1)) #大标题
    
    for j in range(len(data.columns)-1):
        Col = j+1
        #plt.subplot(Pic_col,Pic_col,1+j)
        #plt.subplot(3,2,1+j)       #分表时
        plt.subplot(10,6,1+j)           #不分表时 
        
        y0 = data[data.columns[Col]]  #number   ---Nt
        x0 = data[data.columns[0]]    #time     ----t
        
                ##找最大值
        ymax = 0
        xmax = 0
        for i in range(len(y0)):
            if (y0[i]>ymax):
                ymax = y0[i]
                xmax = i
        
        #保留最大值后5个点
        xmax = xmax+5
        if (xmax > len(x0)):
            xmax = len(x0)
        
        ydata = []
        xdata = []
        #xmax = len(ydata)               #设置x轴长度
        for i in range(xmax):
            if(y0[i] == y0[i]):         #数据不为空
                ydata.append(y0[i])
                xdata.append(x0[i])
                
        plt.scatter(xdata,ydata,1,'red') #绘制点图 
        #plt.scatter(data[data.columns[0]],data[data.columns[Col]],1,'red') #绘制点图 
        
        popt, pcov = curve_fit(func, xdata, ydata, maxfev=100000000,bounds = ([0,0,0],[2,0.1,2])) 
        #popt, pcov = curve_fit(func, xdata, ydata, maxfev=1000000000,bounds = (0,float('inf')))
        y2 = [func(i, popt[0],popt[1],popt[2]) for i in xdata]
        #print("K =",popt[0],"N0 = ",popt[1],"r = ",popt[2])
        list_K.append(popt[0])
        list_N0.append(popt[1])
        list_r.append(popt[2])
        #plt.subplot(c,c,1+j)
        
        #计算曲线并补全
        t = x0

        K = popt[0]
        N0 = popt[1]
        r = popt[2]
        
        y = K / (1 + ( (K - N0) / N0) * np.exp(-r * t))
        plt.plot(t,y)
        
        #plt.plot(xdata,y2)
        
        plt.grid()
        plt.axis([0,len(xdata)/2+2,0.1,1.5])
        #plt.yscale('log')
        #plt.axis([0,16,10e-4,10e-1])
        #plt.axis([0,24,-0.01,0.034])
    
    plt.savefig(OnlyPath+'/FitedODSheet.jpg')  
    plt.figure(figsize=(9,8))
    ws.write(summ+1,0,'1')    #编号
    
    j = 2   #写入第几组的编号
    for i in range(len(list_K)):
        summ = summ+1
        if(summ%7 == 0):
            summ= summ+1
            ws.write(summ,0,j)
            j=j+1
            
        ws.write(summ,1,list_K[i]) #在1行1列写入bit
        ws.write(summ,2,list_N0[i]) #在1行2列写入huang
        ws.write(summ,3,list_r[i]) #在1行3列写入xuan
    summ = summ+1
    
    w.save(OnlyPath+'/Fited_Para.xls')  #保存

    return 




############################################执行#######################



OnlyPath = 'E:/IBIS/细菌利用率实验/20190219/'
fileName = 'CC_test_20190220_LH_Glucose-Anc1.xlsx'
path = OnlyPath+fileName


###输入地址，输出所有数据
alldata = Write_allData(path,OnlyPath)    
###输入地址，输出左右，上下空白数据
lr, un = lrAndUn_Blank(path,OnlyPath)

###输入地址，输出中间部分实验数据
udArray = CentreData(path,OnlyPath,0)

#输出所有孔的数据
Drow_96Well(path,OnlyPath)


#输出中间部分有效数据绘图
ud = Drow_ContreData(path,OnlyPath,lr)

DataFitname = OnlyPath+'usedData.xls'
DataFitting(DataFitname,OnlyPath)

    
    



















