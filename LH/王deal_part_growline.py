import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

excel_row = pd.read_excel('P13.xlsx','板 1 - Sheet1', skiprows = 74, nrows = 49)
excel_row_df = pd.DataFrame(excel_row)
index_del = ['A1','A2','A3','A4','A5','A6',
             'A7','A8','A9','A10','A11','A12',
             'B1','B12','C1','C12','D1','D12',
             'E1','E12','F1','F12','G1','G12',
             'H1','H2','H3','H4','H5','H6',
             'H7','H8','H9','H10','H11','H12']
excel_row_df = excel_row_df.drop(index_del, axis = 1)
#excel_row_df = excel_row_df.drop(columns = [1])
excel_row_df = excel_row_df.iloc[:,1:]
time = pd.Series() 
writer = pd.ExcelWriter('P13_processed.xlsx')
excel_row_df.to_excel(writer, 'Sheet1')
writer.save()
print("job done")
#print(excel_row_df)




excel = pd.read_excel('P13.xlsx','Sheet1')
result_excel1 = pd.read_excel('result.xlsx', 'Sheet1')
result_excel2 = pd.read_excel('result.xlsx', 'Sheet2')

frame = pd.DataFrame(excel)
frame_result1 = pd.DataFrame(result_excel1)
frame_result2 = pd.DataFrame(result_excel2)

'''
'''
time = []
temp_x = frame['Time'] #.apply(lambda x: for x in frame['Time'] x = str(x))

for data in frame['Time']:
    data = data.strftime('%H:%M:%S')
    time.append(data)

time = np.array(time)
time = time.T
'''
'''
temp_x = frame['Time']
frame_1 = frame.iloc[:,3:]
# draw all picture
def single_picture():
    plt.figure(figsize = (10,10), dpi = 100)
    for index, col in frame_1.iteritems():
        plt.plot(temp_x,col, linewidth = 1)
        plt.xticks(fontsize = 25)
        plt.yticks(fontsize = 25)
        plt.ylim(0,0.9)
        plt.xlim(0,25)
        plt.ylabel('OD600_vlaue', fontsize = 25)
        plt.xlabel('Time(H)', fontsize = 25)
        plt.savefig("D:\\MBY growline\\MCL induce L-form\\figure\\2020.07.07\\fig_%s" % index)
        plt.clf()


# draw repeat mean picture
def repeat_mean_picture():
    state = [ 'B2','C2','D2','E2','F2','G2',
              'B3','C3','D3','E3','F3','G3',
              'B4','C4','D4','E4','F4','G4',
              'B5','C5','D5','E5','F5','G5',
              'B6','C6','D6','E6','F6','G6',
              'B7','C7','D7','E7','F7','G7',
              'B8','C8','D8','E8','F8','G8',
              'B9','C9','D9','E9','F9','G9',
              'B10','C10','D10','E10','F10','G10',
              'B11','C11','D11','E11','F11','G11']
    
    frame_2 = frame.iloc[:,2:]
    frame_2_new = frame_2.reindex(columns = state)
    m = 0
    n = 6
    frame_3 = pd.DataFrame()
    for i in range(10):
        frame_2_i = frame_2_new.iloc[:,m:n].mean(axis = 'columns')
        m = n
        n = n + 6
        frame_3[i] = frame_2_i
    for index, col in frame_3.iteritems():
        plt.plot(temp_x,col, linewidth = 1)
        plt.xticks(rotation = 60)
        plt.savefig("D:\\MBY growline\\MCL induce L-form\\figure\\2020.07.01\\fig_%s" % index)
    
def total_picture():
    i = 1
    fig = plt.subplots(6, 10, figsize=(25,10), sharey=True, dpi = 100)
    for index, col in frame_1.iteritems():
        plt.subplot(6,10,i)
        plt.plot(temp_x,col, linewidth = 1)
        plt.ylim(0,0.9)
        plt.xticks([])
        plt.ylabel('ODvalue')
        plt.xlabel('Time(24H)')
        plt.subplots_adjust(left=None, bottom=None, right=None, top=None,wspace=0.5, hspace=None)
        i = i + 1
    plt.savefig("D:\\MBY growline\\MCL induce L-form\\figure\\P13\\fig_all")

def show_result1():
    temp_x = frame_result1['Time']
    frame_result_1 = frame_result1.iloc[:,11:]
    #fig = plt.subplots(2, 5, figsize=(30,13), sharey=True, dpi = 100)
    plt.figure(figsize = (10,10), dpi = 100)
    j = 8
    MCL_Con = [" MCL:16.6 µg/ml"," MCL:5.5 µg/ml"," MCL:16.6 µg/ml"," MCL:16.6 µg/ml"," MCL:16.6 µg/ml",
               " MCL:16.6 µg/ml"," MCL:100 µg/ml"," MCL:50 µg/ml"," MCL:16.6 µg/ml"," MCL:100 µg/ml"]
    for index, col in frame_result_1.iteritems():
        str1 = "P"
        str2 = j + 2
        str3 = MCL_Con[j-2]
        #plt.title(str1+str(str2)+str3, fontsize = 25)
        #plt.subplot(2,5,j)
        plt.plot(temp_x,col, linewidth = 2, label = str1 + str(str2) + str3)
        plt.legend(loc='upper left', scatterpoints=1, fontsize = 20)
        plt.ylim(0,0.6)
        plt.xticks(fontsize = 20)
        plt.yticks(fontsize = 20)
        plt.xlim(0,25)
        plt.ylabel('ODvalue',fontsize = 25)
        plt.xlabel('Time (Hour)',fontsize = 25)
        plt.subplots_adjust(left=None, bottom=None, right=None, top = None,wspace=0.5, hspace=0.5)
        j = j + 1
    #plt.title("P13 MCL: 100 µg/ml", fontsize = 25)
    plt.savefig("D:\\MBY growline\\MCL induce L-form\\figure\\result\\fig_result")

def show_result2():
    fig = plt.subplots(figsize=(15,10), sharey=True, dpi = 100)
    temp_x = frame_result2['generation']
    temp_y = frame_result2['Aspect ratio']
    for x,y in zip(frame_result2['generation'],frame_result2['Aspect ratio']):
        plt.text(x,y,y,ha='center', va='bottom',fontsize = 15)
    p1, = plt.plot(temp_x,temp_y, linewidth = 2, marker='o',markerfacecolor='green', markersize=10)
    temp_y = frame_result2['Aspect ratio Blank']  
    for x,y in zip(frame_result2['generation'],frame_result2['Aspect ratio Blank']):
        plt.text(x,y,y,ha='center', va='bottom',fontsize = 15)
    p2, = plt.plot(temp_x,temp_y, linewidth = 2, marker='o',markerfacecolor='red', markersize=10)
    plt.ylim(0,1)
    plt.legend([p1, p2], ['MCL induced', 'Blank'], loc='lower right', scatterpoints=1, fontsize = 30)
    plt.xticks(fontsize = 20)
    plt.yticks(fontsize = 20)
    plt.ylabel('Aspect ratio value',fontsize = 25)
    plt.xlabel('Generation',fontsize = 25)
    plt.savefig("D:\\MBY growline\\MCL induce L-form\\figure\\result\\fig_result2")


show_result1()
show_result2()
#single_picture()
#total_picture()
#repeat_mean_picture()

