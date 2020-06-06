# coding: UTF-8
from bs4 import BeautifulSoup
import requests
import re
import os
def getPokeList():
    r = requests.get('https://yakkun.com/swsh/zukan/')
    soup = BeautifulSoup(r.content, "html.parser")
    text =soup.find("ul", "pokemon_list").a.get("href")
    links = []
    url = "https://yakkun.com"
    index = 1
    DBmaster=""
    DBtype=""
    DBstatus = ""
    DBchara=""
    DBegg=""
    for a in soup.find("ul", "pokemon_list").find_all('a', href=True): 
        if a.text: 
            urilist=url+a['href']
            print(str(index)+" "+urilist)
            r = requests.get(urilist)
            soup = BeautifulSoup(r.content, "html.parser")

            DBmaster+= createDbMaster(index,soup)
            DBtype+=createDbArr(index,getType(soup))
            DBstatus+=createDbStatus(index,soup)
            DBchara+=createDbArr(index,getCharacteristics(soup))
            DBegg+=createDbArr(index,getEggCategory(soup))
            
            
            index+=1
    createCSV("master.csv",DBmaster)
    createCSV("type.csv",DBtype)
    createCSV("status.csv",DBstatus)
    createCSV("characteristics.csv",DBchara)
    createCSV("eggCategory.csv",DBegg)
            


def createCSV(name,text):
    file = open(name, 'w')
    file.write(text)
    file.close()

def createDbMaster(index,soup):
    name = getName(soup)
    num = getNum(soup)
    return str(index)+","+str(num)+","+name+"\n"
def createDbArr(index,arr):
    text=""
    num=1
    for i in arr:
        text+=str(index)+","+str(num)+","+str(i)+"\n"
        num+=1
    return text

def createDbStatus(index,soup):
    status=getParams(soup)
    text=str(index)
    for i in status:
        text+=","+i
    return text+"\n"



def encodeArr(arr,n):
    num=len(arr)
    
    s=""
    for i in range(num):
        s+=arr[i]+","
    if num!=n:
        for i in range(n-num):
            s+=","
    return s[:-1]
        
def getType(soup):
    types = [] 
    text = soup.find("ul","type").find_all("li")
    for a in text:
        types.append(a.find("img").get("alt"))
    return types

def getName(soup):
    text = soup.find("tr","head").text
    return text
def getNum(soup):
    num = soup.find("div",class_="table").table.find_all("tr")
    for i in num:
        if str(i).find("ガラルNo.")!=-1:
            text=i.find_all("td")[1].text
            # print(text)
            return text

def getParams(soup):
    index=0
    arr = []
    text = soup.find_all("table",class_="center",summary="詳細データ")[0].find_all("tr")
    for i in text:
        str1 = i.text.split(" ")
        for i in str1:
            str2 = re.split("\xa0",str(i))[1]
            arr.append(str2)
        index = index +1
        if index == 7:
            break
    arr.pop(0)
    return arr
def getCharacteristics(soup):
    text = soup.find_all("table",class_="center",summary="詳細データ")[0].find_all("tr")
    arr =[]
    for i in text:
        if str(i).find("tokusei")!=-1:
            arr.append(i.td.a.text)
    return arr

def getEggCategory(soup):
    arr =[]
    text = soup.find_all("table",class_="center",summary="詳細データ")[0].find_all("tr")
    for i in text:
        if str(i).find("group")!=-1:
            egg = i.find_all("a")
            for a in egg:
                arr.append(a.text)
    return arr

def getCharacteristicsList():
    baseUrl = "https://yakkun.com/swsh/zukan/search/?tokusei="
    index = 1
    KIND_OF_CHARACTERISTICS = 258
    tokusei="あくしゅう,あめふらし,かそく,カブトアーマー,がんじょう,しめりけ,じゅうなん,すながくれ,せいでんき,ちくでん,ちょすい,どんかん,ノーてんき,ふくがん,ふみん,へんしょく,めんえき,もらいび,りんぷん,マイペース,きゅうばん,いかく,かげふみ,さめはだ,ふしぎなまもり,ふゆう,ほうし,シンクロ,クリアボディ,しぜんかいふく,ひらいしん,てんのめぐみ,すいすい,ようりょくそ,はっこう,トレース,ちからもち,どくのトゲ,せいしんりょく,マグマのよろい,みずのベール,じりょく,ぼうおん,あめうけざら,すなおこし,プレッシャー,あついしぼう,はやおき,ほのおのからだ,にげあし,するどいめ,かいりきバサミ,ものひろい,なまけ,はりきり,メロメロボディ,プラス,マイナス,てんきや,ねんちゃく,だっぴ,こんじょう,ふしぎなうろこ,ヘドロえき,しんりょく,もうか,げきりゅう,むしのしらせ,いしあたま,ひでり,ありじごく,やるき,しろいけむり,ヨガパワー,シェルアーマー,エアロック,ちどりあし,でんきエンジン,とうそうしん,ふくつのこころ,ゆきがくれ,くいしんぼう,いかりのつぼ,かるわざ,たいねつ,たんじゅん,かんそうはだ,ダウンロード,てつのこぶし,ポイズンヒール,てきおうりょく,スキルリンク,うるおいボディ,サンパワー,はやあし,ノーマルスキン,スナイパー,マジックガード,ノーガード,あとだし,テクニシャン,リーフガード,ぶきよう,かたやぶり,きょううん,ゆうばく,きけんよち,よちむ,てんねん,いろめがね,フィルター,スロースタート,きもったま,よびみず,アイスボディ,ハードロック,ゆきふらし,みつあつめ,おみとおし,すてみ,マルチタイプ,フラワーギフト,ナイトメア,わるいてぐせ,ちからずく,あまのじゃく,きんちょうかん,まけんき,よわき,のろわれボディ,いやしのこころ,フレンドガード,くだけるよろい,ヘヴィメタル,ライトメタル,マルチスケイル,どくぼうそう,ねつぼうそう,しゅうかく,テレパシー,ムラっけ,ぼうじん,どくしゅ,さいせいりょく,はとむね,すなかき,ミラクルスキン,アナライズ,イリュージョン,かわりもの,すりぬけ,ミイラ,じしんかじょう,せいぎのこころ,びびり,マジックミラー,そうしょく,いたずらごころ,すなのちから,てつのトゲ,ダルマモード,しょうりのほし,ターボブレイズ,テラボルテージ,ほおぶくろ,フラワーベール,ファーコート,バトルスイッチ,スイートベール,がんじょうあご,フリーズスキン,かたいツメ,メガランチャー,おやこあい,フェアリーオーラ,ダークオーラ,オーラブレイク,マジシャン,へんげんじざい,アロマベール,くさのけがわ,ぼうだん,かちき,きょうせい,ぬめぬめ,はやてのつばさ,フェアリースキン,スカイスキン,はじまりのうみ,おわりのだいち,デルタストリーム,はりこみ,バッテリー,ぎゃくじょう,ビビッドボディ,ぜったいねむり,ふしょく,にげごし,ヒーリングシフト,じきゅうりょく,もふもふ,ばけのかわ,おどりこ,リミットシールド,ぎょぐん,とびだすなかみ,サーフテール,みずがため,レシーバー,きずなへんげ,ARシステム,じょおうのいげん,エレキメイカー,サイコメイカー,グラスメイカー,ミストメイカー,メタルプロテクト,ファントムガード,ゆきかき,えんかく,うるおいボイス,ひとでなし,すいほう,ききかいひ,はがねつかい,ビーストブースト,プリズムアーマー,ソウルハート,カーリーヘアー,エレキスキン,かがくのちから,スワームチェンジ,ブレインフォース,ふとうのけん,ふくつのたて,リベロ,たまひろい,わたげ,スクリューおびれ,ミラーアーマー,うのミサイル,すじがねいり,じょうききかん,パンクロック,すなはき,こおりのりんぷん,じゅくせい,アイスフェイス,パワースポット,ぎたい,バリアフリー,はがねのせいしん,ほろびのボディ,さまようたましい,ごりむちゅう,かがくへんかガス,パステルベール,はらぺこスイッチ"
    tlist=re.split(",",tokusei)
    tokuseiList=""
    for i in range(index,KIND_OF_CHARACTERISTICS+1):
        url=baseUrl+str(i)
        r = requests.get(url)
        soup = BeautifulSoup(r.content, "html.parser")
        text=soup.find("div","contents").find_all("div")[1].p.text
        print(tlist[i-1]+","+text)



# r = requests.get("https://yakkun.com/swsh/zukan/n263g")
# soup = BeautifulSoup(r.content, "html.parser")
# getCharacteristicsList()
# getPokeImgUrl(soup)
# renameFileShow()
getPokeList()
