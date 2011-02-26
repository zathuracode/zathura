package com.vortexbird.amazilia.fw;

import java.beans.PropertyChangeListener;

import net.sourceforge.squirrel_sql.fw.id.IIdentifier;
import net.sourceforge.squirrel_sql.fw.persist.ValidationException;
import net.sourceforge.squirrel_sql.fw.sql.ISQLAlias;
import net.sourceforge.squirrel_sql.fw.sql.SQLDriverPropertyCollection;

public class AmaziliaSQLAlias implements ISQLAlias {
	
	private String url;
	private String name;
	private String password;
	private String userName;
	private boolean autoLogon;
	private boolean valid;
	

	
	
	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public int compareTo(Object rhs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IIdentifier getDriverIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLDriverPropertyCollection getDriverPropertiesClone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		
		return name;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUrl() {
		
		return url;
	}

	@Override
	public boolean getUseDriverProperties() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return userName;
	}

	@Override
	public boolean isAutoLogon() {
		// TODO Auto-generated method stub
		return autoLogon;
	}

	@Override
	public boolean isConnectAtStartup() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAutoLogon(boolean value) {
		this.autoLogon=value;

	}

	@Override
	public void setConnectAtStartup(boolean value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDriverIdentifier(IIdentifier data)
			throws ValidationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDriverProperties(SQLDriverPropertyCollection value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setName(String name) throws ValidationException {
		this.name=name;

	}

	@Override
	public void setPassword(String password) throws ValidationException {
		this.password=password;

	}

	@Override
	public void setUrl(String url) throws ValidationException {
		this.url=url;
	}

	@Override
	public void setUseDriverProperties(boolean value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUserName(String userName) throws ValidationException {
		this.userName=userName;

	}

	@Override
	public IIdentifier getIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid() {
		return valid;
	}

}
