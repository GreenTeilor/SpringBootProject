package by.teachmeskills.springbootproject.endpoint;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileReader;
import java.io.IOException;

@Component
@Endpoint(id = "pomInfo")
public class PomInfoEndpoint {

    @ReadOperation
    public ModelAndView getPomInfo() throws XmlPullParserException, IOException {
        ModelMap modelMap = new ModelMap();
        ModelAndView modelAndView = new ModelAndView("pomInfo");
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model;
        model = reader.read(new FileReader("pom.xml"));
        modelMap.addAttribute("projectFullId", model.getId());
        modelMap.addAttribute("groupId", model.getGroupId());
        modelMap.addAttribute("artifactId", model.getArtifactId());
        modelMap.addAttribute("version", model.getVersion());
        modelMap.addAttribute("packaging", model.getPackaging());
        modelMap.addAttribute("name", model.getName());
        modelMap.addAttribute("description", model.getDescription());
        modelAndView.addAllObjects(modelMap);
        return modelAndView;
    }
}
