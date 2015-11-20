package cn.kane.web.view.viewresolver.support.velocity.service;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.parser.ParseException;
import org.apache.velocity.runtime.parser.node.Node;

@Deprecated
public class VelocityExtConfigServiceImpl implements VelocityExtConfigService {

	@Override
	public void upateMacro(String macroName, String content,String sourceTemplate, String[] argArray) {
		try {
			Node macroNode = RuntimeSingleton.parse(new InputStreamReader(new ByteArrayInputStream(content.getBytes())),sourceTemplate);
			RuntimeSingleton.addVelocimacro(macroName,macroNode , argArray, sourceTemplate) ;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object costexp(Long costMillis) {
		try {
			Thread.sleep(costMillis);
			return costMillis ;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

}
