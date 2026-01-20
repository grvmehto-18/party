import org.moqui.entity.EntityValue

def ec = context.ec
def errorValue;

if (!partyId) {
    errorValue = "partyId"
    ec.message.addError("${errorValue} is required")
}
if (!firstName || !lastName) {
    errorValue = !firstName ? "firstName": "lastName"
    ec.message.addError("${errorValue} is required")
}

if (ec.message.hasError()) {
    return
}
EntityValue party = ec.entity.find("tutorial.party.Party")
        .condition("partyId", partyId)
        .one()

if (!party) {
    ec.message.addError("No Party found for partyId: ${partyId}")
    return
}

EntityValue person = ec.entity.makeValue("tutorial.party.Person")

person.partyId = partyId
person.firstName = firstName
person.lastName = lastName
person.dateOfBirth = dateOfBirth

person.create()

context.responseMessage =
        "Person ${firstName} ${lastName} created successfully!"
