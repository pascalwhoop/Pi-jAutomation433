package com.opitz.iotprototype.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

import com.opitz.iotprototype.entities.ElroPowerPlug;

public class DroolsDelegate implements JavaDelegate {

	private static final String PLUG_RULES_FILE = "plugRules.drl";

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		String requestedState = (String) execution.getVariable("state");
		ElroPowerPlug elroPowerPlug = (ElroPowerPlug) execution.getVariable("plug");

		System.out.println("## camunda: checking rules for plug (id = "
				+ elroPowerPlug.getId() + ") to switch to " + requestedState + ". ##");

		// TODO finalize impl

		KnowledgeBase knowledgeBase = createKnowledgeBase();
		StatefulKnowledgeSession workingMemory = knowledgeBase
				.newStatefulKnowledgeSession();
		workingMemory.setGlobal("requestedState", requestedState);
		try {
			workingMemory.insert(elroPowerPlug);

			workingMemory.fireAllRules();
		} finally {
			workingMemory.dispose();
		}

		// update variable in process variables
		execution.setVariable("plug", elroPowerPlug);

	}

	/**
	 * Create new knowledge base.
	 * 
	 * @return new {@link KnowledgeBase}
	 */
	private static KnowledgeBase createKnowledgeBase() {
		KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		builder.add(ResourceFactory.newClassPathResource(PLUG_RULES_FILE),
				ResourceType.DRL);

		if (builder.hasErrors()) {
			throw new RuntimeException(builder.getErrors().toString());
		}
		KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
		knowledgeBase.addKnowledgePackages(builder.getKnowledgePackages());
		return knowledgeBase;
	}

}
