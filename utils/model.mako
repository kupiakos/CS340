package ${package};

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;
%for pkg in model.includes:
import ${pkg};
%endfor

%if model.docs:
/**
 * ${model.docs}
 */
%endif
@Generated("net.kupiakos")
public class ${model.name} {

    %for prop in model.properties:
    @SerializedName("${prop.serial_name}")
    @Expose${'' if prop.can_set else '(deserialize = false)'}
    private ${prop.javatype} ${prop.name}${prop.init};

    %endfor

    // CUSTOM CODE
    // END CUSTOM CODE

    %if model.properties:
    /**
     * No args constructor for use in serialization
     */
    public ${model.name}() {
    }
    %endif

    /**
    %for prop in model.properties:
      * @param ${prop.name} ${prop.docs}
    %endfor
     */
    public ${model.name}(${', '.join('%s %s' %(prop.javatype, prop.name) for prop in model.properties if prop.can_set)}) {
        %for prop in model.properties:
        %if prop.can_set:
            this.${prop.name} = ${prop.name};
        %endif
        %endfor
    }

    %for prop in model.properties:
    /**
     * @return ${prop.docs}
     */
    public ${prop.javatype} ${prop.getter}() { return ${prop.name}; }

    %if prop.can_set:
    /**
     * @param ${prop.name} ${prop.docs}
     */
    public void ${prop.setter}(${prop.javatype} ${prop.name}) { this.${prop.name} = ${prop.name}; }

    public ${model.name} ${prop.wither}(${prop.javatype} ${prop.name}) {
        ${prop.setter}(${prop.name});
        return this;
    }
    %endif
    %endfor

    @Override
    public String toString() {
        %if model.properties:
        return "${model.name} [" +
        %for n, prop in enumerate(model.properties):
            "${', ' if n else ''}${prop.name}=" + ${prop.name} +
        %endfor
            "]";
        %else:
        return "${model.name} []";
        %endif
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ${model.name}) {
            %if model.properties:
            return equals((${model.name})other);
            %else:
            return true;
            %endif
        }
        return false;
    }

    %if model.properties:
    public boolean equals(${model.name} other) {
        return (
        %for n, prop in enumerate(model.properties):
            ${prop.name} == other.${prop.name}${' &&' if n < len(model.properties) - 1 else ''}
        %endfor
        );
    }
    %endif
}
