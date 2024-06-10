from flask import *
from database import *

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



admin=Blueprint('admin',__name__)

@admin.route('/adminhome',methods=['get','post'])
def adminhome():
    return render_template('adminhome.html')


@admin.route('/admin_manage_policy',methods=['get','post'])
def admin_manage_policy():
    data={}
    if 'btn' in request.form:
        policy=request.form['policy']
        amount=request.form['amount']
        no_days=request.form['no_days']
        
    
        q="insert into policy values (null,'%s','%s','%s')"%(policy,amount,no_days)
        insert(q)
        flash("Successfully Added")
        return redirect(url_for("admin.admin_manage_policy"))

    data={}
    q="select * from policy"
    data['res']=select(q)
    data['count']=len(select(q))

    if 'action' in request.args:
        action=request.args['action']
        pid=request.args['pid'] 
    else:
        action=None

    
    if action == "update":
        q="select * from policy where policy_id='%s'"%(pid)
        val=select(q)
        data['raw']=val

        if 'update' in request.form:
            policy=request.form['policy']
            amount=request.form['amount']
            no_days=request.form['no_days']

            q="update policy set policy='%s', amount='%s', no_ofdays='%s' where policy_id='%s' "%(policy,amount,no_days,pid)
            update(q)
            flash("Updated Successfully")
            return redirect(url_for("admin.admin_manage_policy"))
    if action == "delete":
        q="delete from policy where policy_id='%s' "%(pid)
        delete(q)
        flash("Deleted Successfully")
        return redirect(url_for("admin.admin_manage_policy"))
    return render_template('admin_manage_policy.html',data=data) 


@admin.route('/admin_view_policyreq')
def admin_view_policyreq():
    data={}
    q="SELECT * FROM `policy`,`policyrequest`,`agent` WHERE `policy`.`policy_id`=`policyrequest`.`policy_id` AND `policyrequest`.`agent_id`=`agent`.`agent_id`"
    res=select(q)
   
    data['view']=res

    if 'action' in request.args:
        action=request.args['action']
        policyrequest_id=request.args['policyrequest_id']
        agent_id=request.args['agent_id'] 
        policy_id=request.args['policy_id']
        vechiclenum=request.args['vechiclenum']
        modelnum=request.args['modelnum']
        enginenum=request.args['enginenum']
        status=request.args['status']
    else:
        action=None

    if action == "approve":
        q="update policyrequest set status='Approved' where policyrequest_id='%s'"%(policyrequest_id)
        update(q)
        import datetime
        d=datetime.datetime.now().strftime("%d-%m-%Y %H:%M:%S")
        with open(compiled_contract_path) as file:
            contract_json = json.load(file)  # load contract info as JSON
            contract_abi = contract_json['abi']  # fetch contract's abi - necessary to call its functions
            contract = web3.eth.contract(address=deployed_contract_address, abi=contract_abi)
            id=web3.eth.get_block_number()
        message = contract.functions.acceptedrequest(id,int(agent_id),int(policy_id),vechiclenum,modelnum,enginenum,d,'Approved').transact()

        return redirect(url_for("admin.admin_view_policyreq"))
    if action == "reject":
        q="update policyrequest set status='Rejected' where policyrequest_id='%s'"%(policyrequest_id)
        update(q)
        return redirect(url_for("admin.admin_view_policyreq"))

    return render_template('admin_view_policyreq.html',data=data)



@admin.route('/admin_view_damagedreq')
def admin_view_damagedreq():
    data={}
    q="SELECT *,damagerequest.date as date,damagerequest.status as status FROM `policy`,`policyrequest`,`agent`,`damagerequest` WHERE `policy`.`policy_id`=`policyrequest`.`policy_id` AND `policyrequest`.`agent_id`=`agent`.`agent_id` AND `damagerequest`.`policyrequest_id` = `policyrequest`.`policyrequest_id`"
    data['res']=select(q)

    if 'action' in request.args:
        action=request.args['action']
        did=request.args['did'] 
    else:
        action=None

    if action == "approve":
        q="update damagerequest set status='Approved' where damagerequest_id='%s'"%(did)
        update(q)
        return redirect(url_for("admin.admin_view_damagedreq"))
    if action == "reject":
        q="update damagerequest set status='Rejected' where damagerequest_id='%s'"%(did)
        update(q)
        return redirect(url_for("admin.admin_view_damagedreq"))

    return render_template('admin_view_damagedreq.html',data=data)



@admin.route('/admin_view_complaints',methods=['get','post'])
def admin_view_complaints():
    data={}
    q="select * from complaint inner join agent using (agent_id)"
    res=select(q)
    data['res']=res

    for i in range(1,len(res)+1):
        if 'btn'+str(i) in request.form:
          
            comp_id=request.form['cid'+str(i)]
            reply=request.form['reply'+str(i)]
            print("sssssssssssssssssssssssssssss",comp_id,reply)
            q="update complaint set reply='%s' where complaint_id='%s'"%(reply,comp_id)
            update(q)
            return redirect(url_for("admin.admin_view_complaints"))
    return render_template('admin_view_complaints.html',data=data)