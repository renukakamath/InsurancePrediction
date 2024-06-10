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


agent=Blueprint('agent',__name__)

@agent.route('/agenthome',methods=['get','post'])
def agenthome():
    return render_template('agenthome.html')


@agent.route('/agent_view_policy')
def agent_view_policy():
    data={}
    q="select * from policy"
    data['res']=select(q)
    return render_template('agent_view_policy.html',data=data)


@agent.route('/agent_request_policy',methods=['get','post'])
def agent_request_policy():
    data={}
    pid=request.args['pid']
    if 'btn' in request.form:
        vnum=request.form['vnum']
        mnum=request.form['mnum']
        enum=request.form['enum']

        q="insert into policyrequest values (null,'%s','%s','%s','%s','%s',curdate(),'pending')"%(session['aid'],pid,vnum,mnum,enum)
        insert(q)

        
        flash("Request Send Successfully")
        return redirect(url_for("agent.agenthome"))

    return render_template('agent_request_policy.html',data=data)


@agent.route('/agent_view_mypolicyreq')
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
    data['res']=res

    if 'action' in request.args:
        action=request.args['action']
        prid=request.args['prid'] 
    else:
        action=None
    
    if action=="showstat":
        q="select * from policy inner join policyrequest using (policy_id) where agent_id='%s' and policyrequest_id='%s'"%(session['aid'],prid)
        data['showv']=select(q)

    
    return render_template('agent_view_mypolicyreq.html',data=data)



import uuid
@agent.route('/agent_damage_request',methods=['get','post'])
def agent_damage_request():
    data={}
    amount=""
    q="select * from policy inner join policyrequest using (policy_id)"
    data['preq']=select(q)
    if 'btn' in request.form:
        plid=request.form['plid']
        image=request.files['image']
        path="static/uploads/"+str(uuid.uuid4())+image.filename
        image.save(path)

        
        res=predictcnn(path)
        msg="50"
        amount="50000"
        if str(res)=="0":
            msg="10"
            amount="15000"
        elif str(res)=="1":
            msg="30"
            amount="28000"
        print ("sssssssssssssssssssssssssssssssssss",amount)
        q="insert into damagerequest values (null,'%s','%s','%s','%s',curdate(),'pending')"%(session['aid'],plid,path,amount)
        id=insert(q)
        flash("Request Completed!")
        return redirect(url_for("agent.agenthome"))
    
    # if 'action' in request.args:
    #     action=request.args['action']
    # else:
    #     action=None
    # if action == "confirm":
    #     q="update damagerequest set price='%s' where damagerequest_id='%s'"%(amount,id)
    #     update(q)
    #     flash("Request Completed!")
    #     return redirect(url_for("agent.agenthome"))
    
    # if action == "cancel":
    #     q="delete from damagerequest  where damagerequest_id='%s'"%(id)
    #     update(q)
    #     flash("Request Canceled!")
    #     return redirect(url_for("agent.agenthome"))


    return render_template('agent_damage_request.html',data=data,amount=amount)



@agent.route('/agent_view_damagereq')
def agent_view_damagereq():
    data={}
    q="select *,damagerequest.date as date,damagerequest.status as status  from policy inner join policyrequest using (policy_id) inner join damagerequest using (policyrequest_id)  where damagerequest.agent_id='%s'"%(session['aid'])
    data['res']=select(q)

    if 'action' in request.args:
        action=request.args['action']
        did=request.args['did'] 
    else:
        action=None
    
    if action=="showstat":
        q="select *,damagerequest.date as date,damagerequest.status as status  from policy inner join policyrequest using (policy_id) inner join damagerequest using (policyrequest_id) where damagerequest.agent_id='%s' and damagerequest_id='%s'"%(session['aid'],did)
        data['showv']=select(q)

    
    return render_template('agent_view_damagereq.html',data=data)


@agent.route("/agent_send_complaint",methods=['get','post'])
def agent_send_complaint():
    data={}

    cid=session['aid']

    if 'btn' in request.form:
        comp=request.form['comp']

        q="insert into complaint values(NULL,'%s','%s','pending',curdate())"%(cid,comp)
        insert(q)
        flash("Complaint Added")
        return redirect(url_for("agent.agent_send_complaint"))
    
    q="select * from complaint where agent_id='%s'"%(cid)
    data['res']=select(q)
    return render_template("agent_send_complaint.html",data=data)