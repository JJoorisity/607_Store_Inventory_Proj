package client.clientViews;

import javax.annotation.PostConstruct;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class CmsApplication {

	@PostConstruct
    public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
        System.out.println(this.getClass().getSimpleName()
        + " @PostConstruct method called.");
    }
}
