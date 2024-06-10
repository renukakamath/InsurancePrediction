from flask import *
from database import *
from newcnn import predictcnn

import json
from web3 import Web3, HTTPProvider

# truffle development blockchain address
blockchain_address = 'http://127.0.0.1:9545'
# Client instance to interact with the blockchain
web3 = Web3(HTTPProvider(blockchain_address))
# Set the default account (so we don't need to set the "from" for every transaction call)
web3.eth.defaultAccount = web3.eth.accounts[0]
compiled_contract_path = 'C:/Users/renuk/OneDrive/Desktop/RISS/python/Insurance Prediction/node_modules/.bin/build/contracts/insuranceprediction.json'
# compiled_contract_path = 'F:/NGO/node_modules/.bin/build/contracts/medicines.json'
# Deployed contract address (see `migrate` command output: `contract address`)
deployed_contract_address = '0xA7BBe41BCa5606342D92D6Da0E3db2CCb1aE1055'


api=Blueprint('api',__name__)

@api.route('/reg',methods=['get','post'])
def reg():
	data={}
	fname=request.args['fname']
	lname=request.args['lname']
	gender=request.args['gender']
	place=request.args['place']
	pin=request.args['pincode']
	phone=request.args['phone']
	email=request.args['email'] 
	uname=request.args['username'] 
	passw=request.args['password'] 

	q="select * from login where username='%s'"%(uname)
	res=select(q)
	if res:
	   data['status']="duplicate"
	else:
	    q="insert into `login` values(NULL,'%s','%s','agent')"%(uname,passw)
	    res=insert(q)

	    w="insert into agent value(NULL,'%s','%s','%s','%s','%s','%s','%s','%s')"%(res,fname,lname,gender,place,pin,email,phone)
	    insert(w)

	data['status']="success"
	return str(data)


@api.route('/logins',methods=['post','get'])
def logins():
	data={}
	uname=request.args['username']
	pasw =request.args['password']
	q="select * from login where username='%s' and password='%s'"%(uname,pasw)
	res=select(q)
	data['data']=res
	data['status']="success"
	return str(data)


@api.route('/agent_view_policy')
def agent_view_policy():
	data={}
	q="select * from policy"
	res=select(q)
	data['data']=res
	data['status']="success"
	return str(data)



@api.route('/agent_request_policy',methods=['get','post'])
def agent_request_policy():
    data={}
    pid=request.args['pid']
   
    vnum=request.args['vnum']
    mnum=request.args['mnum']
    enum=request.args['enum']
    aid=request.args['login_id']

    q="insert into policyrequest values (null,'%s','%s','%s','%s','%s',curdate(),'pending')"%(aid,pid,vnum,mnum,enum)
    insert(q)
    data['status']="success"
    data['method']="user_requestacc"
    return str(data)



@api.route('/agent_view_mypolicyreq')
def agent_view_mypolicyreq():
    data={}
    # q="select * from policy inner join policyrequest using (policy_id) where agent_id='%s'"%(session['aid'])
    with open(compiled_contract_path) as file:
        contract_json = json.load(file)  # load contract info as JSON
        contract_abi = contract_json['abi']  # fetch contract's abi - necessary to call its functions
    contract = web3.eth.contract(address=deployed_contract_address, abi=contract_abi)
    blocknumber = web3.eth.get_block_number()
    res = []
    try:
        for i in range(blocknumber, 0, -1):
            a = web3.eth.get_transaction_by_block(i, 0)
            decoded_input = contract.decode_function_input(a['input'])
            print(decoded_input)
            if str(decoded_input[0]) == "<Function acceptedrequest(uint256,uint256,uint256,string,string,string,string,string)>":
                # if int(decoded_input[1]['u_id']) == int(session['user_id']):
                    res.append(decoded_input[1])
    except Exception as e:
        print("", e)
   
    data['data']=res

    
    data['status']="success"
    return str(data)


@api.route('/dropdownpolice')
def dropdownpolice():
	data={}
	q="select * from policyrequest"
	res=select(q)
	
	data['data']=res
	data['status']="success"
	data['method']="viewproductspinner"
	return str(data)




import uuid
@api.route('/agent_damage_request/',methods=['get','post'])
def agent_damage_request():
    data={}
    amount=""
    # q="select * from policy inner join policyrequest using (policy_id)"
    # data['preq']=select(q)
    log_id=request.form['log_id']
   
    plid=request.form['pid']
    image1=request.files['image']
    path1="static/uploads/"+str(uuid.uuid4())+image1.filename
    image1.save(path1)
    image2=request.files['image1']
    path2="static/uploads/"+str(uuid.uuid4())+image2.filename
    image2.save(path2)
    image3=request.files['image2']
    path3="static/uploads/"+str(uuid.uuid4())+image3.filename
    image3.save(path3)
    image4=request.files['image3']
    path4="static/uploads/"+str(uuid.uuid4())+image4.filename
    image4.save(path4)


    q="select * from policy inner join policyrequest using (policy_id)"
    res=select(q)




    res=predictcnn(path1)


    msg="50"
    amount="50000"
    if str(res)=="0":
        msg="10"
        amount="15000"
    elif str(res)=="1":
        msg="30"
        amount="28000"

    elif str(res)=="3":
    	msg="50"
    	amount="50000"
    print ("sssssssssssssssssssssssssssssssssss",msg)
    q="insert into damagerequest values (null,(select agent_id from agent where login_id='%s'),'%s','%s','%s',curdate(),'pending','%s','%s','%s')"%(log_id,plid,path1,msg,path2,path3,path4)
    id=insert(q)
    data['status']="success"
    data['method']="upload_image"
    return str(data)
       
   

@api.route('/agent_view_damagereq')
def agent_view_damagereq():
    data={}
    lid=request.args['login_id']
    q="select *,damagerequest.date as date,damagerequest.status as status  from policy inner join policyrequest using (policy_id) inner join damagerequest using (policyrequest_id)  where damagerequest.agent_id=(select agent_id from agent where login_id='%s')"%(lid)
    res=select(q)
    print(q)
    data['data']=res
    data['status']="success"
    return str(data)


@api.route('/agent_view_damagereqs')
def agent_view_damagereqs():
    data={}
    lid=request.args['login_id']
    did=request.args['did']
    q="select *,damagerequest.date as date,damagerequest.status as status  from policy inner join policyrequest using (policy_id) inner join damagerequest using (policyrequest_id)  where damagerequest.agent_id=(select agent_id from agent where login_id='%s')  and damagerequest_id='%s'"%(lid,did)
    res=select(q)
    print(q)
    data['data']=res
    data['status']="success"
    return str(data)



    # if 'action' in request.args:
    #     action=request.args['action']
    #     did=request.args['did'] 
    # else:
    #     action=None
    
    # if action=="showstat":
    #     q="select *,damagerequest.date as date,damagerequest.status as status  from policy inner join policyrequest using (policy_id) inner join damagerequest using (policyrequest_id) where damagerequest.agent_id='%s' and damagerequest_id='%s'"%(session['aid'],did)
    #     data['showv']=select(q)

    
    # return render_template('agent_view_damagereq.html',data=data)





@api.route("/agent_send_complaint",methods=['get','post'])
def agent_send_complaint():
    data={}

    cid=request.args['login_id']
    comp=request.args['complaint']

    q="insert into complaint values(NULL,(select agent_id from agent where login_id='%s'),'%s','pending',curdate())"%(cid,comp)
    insert(q)
    data['status']="success"
    data['method']="send_complaint"
    return str(data)



@api.route('/view_complaints')
def view_complaints():
	data={}
	cid=request.args['login_id']
	q="select * from complaint where agent_id=(select agent_id from agent where login_id='%s')"%(cid)
	res=select(q)
	data['status']=res
	data['method']="view_complaints"
	return str(data)
   

        
      
    



           